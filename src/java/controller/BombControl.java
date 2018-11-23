package controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import main.BombermanType;
import main.Main;

import java.util.List;
import java.util.stream.Collectors;

import static com.almasb.fxgl.app.DSLKt.play;
import static com.almasb.fxgl.app.DSLKt.texture;


public class BombControl extends Component {
    private int radius;
    private PositionComponent position;
    private int xCenter;
    private int yCenter;

    public BombControl(int radius) {
        this.radius = radius;
    }

    @Override
    public void onUpdate(double tpf) {
    }

    public boolean removeBrick(Entity entity, int x, int y) {
        Texture view = texture("Grass/grasspecies2.png");
        Texture flameCenterView = texture("Flame/flame_center.png");
        Texture flameDownView = texture("Flame/flame_down.png");
        Texture flameHitView = texture("Flame/flame_down1.png");
        if (entity.isType(BombermanType.WALL)) {
            return false;
        } else if (entity.isType(BombermanType.BRICK)) {
            Entity flame = FXGL.getApp()
                    .getGameWorld()
                    .spawn("Flame", new SpawnData(x * Main.TILE_SIZE, y * Main.TILE_SIZE));

            FXGL.getMasterTimer().runOnceAfter(() -> {
                entity.setType(BombermanType.GRASS);
                flame.getComponent(FlameControl.class).burn();
                entity.setViewWithBBox(view);
                entity.setRenderLayer(RenderLayer.BACKGROUND);
            }, Duration.seconds(0.7));

            return false;
        } else if(entity.isType(BombermanType.GRASS) || entity.isType(BombermanType.GRASS) || entity.isType(BombermanType.BOMB)){
            entity.setViewWithBBox(flameDownView);
            if (!entity.isType(BombermanType.BOMB)) {
                FXGL.getMasterTimer().runOnceAfter(() -> {
                    entity.setViewWithBBox(view);
                }, Duration.seconds(0.7));
                return true;
            }
        } else if (entity.isType(BombermanType.GRASS)) {
            entity.setViewWithBBox(flameHitView);
            FXGL.getMasterTimer().runOnceAfter(() -> {
                entity.setViewWithBBox(view);
            }, Duration.seconds(0.7));
            return true;
        }
        return false;
    }


    public void excRemove(List<Entity> entities, int xCenter, int yCenter, boolean width, boolean height) {
        int x, y;
        boolean removed;
        System.out.println(entities.size());
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            x = entity.getPositionComponent().getGridX(Main.TILE_SIZE);
            y = entity.getPositionComponent().getGridY(Main.TILE_SIZE);

            if ((y > yCenter && height) || (x > xCenter && width)) {
                removed = removeBrick(entity, x, y);
                if (!removed) break;
            }
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity entity = entities.get(i);
            x = entity.getPositionComponent().getGridX(Main.TILE_SIZE);
            y = entity.getPositionComponent().getGridY(Main.TILE_SIZE);
            System.out.println(x + " " + y);

            if ((y < yCenter && height) || (x < xCenter && width)) {
                removed = removeBrick(entity, x, y);
                if (!removed) break;
            }
        }
        entities.clear();
    }

    public void explode(int xCenter, int yCenter) {
        play("flame.wav");
        BoundingBoxComponent bbox = getEntity().getBoundingBoxComponent();
        this.xCenter = xCenter;
        this.yCenter = yCenter;

        List<Entity> entitiesHeight = FXGL.getApp()
                .getGameWorld()
                .getEntitiesInRange(bbox.range(0, Main.TILE_SIZE * 2))
                .stream()
                .filter(e -> e.isType(BombermanType.BRICK) || e.isType(BombermanType.WALL) || e.isType(BombermanType.GRASS))
                .collect(Collectors.toList());

        List<Entity> entitiesWidth = FXGL.getApp()
                .getGameWorld()
                .getEntitiesInRange(bbox.range(Main.TILE_SIZE * 2, 0))
                .stream()
                .filter(e -> e.isType(BombermanType.BRICK) || e.isType(BombermanType.WALL) || e.isType(BombermanType.GRASS))
                .collect(Collectors.toList());

        List<Entity> entityCenter = FXGL.getApp()
                .getGameWorld()
                .getEntitiesByType(BombermanType.FLAME, BombermanType.GRASS, BombermanType.BOMB)
                .stream()
                .filter(e -> e.getPositionComponent().getGridX(Main.TILE_SIZE) == xCenter && e.getPositionComponent().getGridY(Main.TILE_SIZE) == yCenter)
                .collect(Collectors.toList());

        removeBrick(entityCenter.get(0), xCenter, yCenter);

        excRemove(entitiesHeight, xCenter, yCenter, false, true);
        excRemove(entitiesWidth, xCenter, yCenter, true, false);
        getEntity().removeFromWorld();
    }
}
