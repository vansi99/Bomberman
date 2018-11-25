package controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;
import main.BombermanType;
import main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.almasb.fxgl.app.DSLKt.play;
import static com.almasb.fxgl.app.DSLKt.texture;


public class BombControl extends Component {

    public void setLength(int length) {
        this.length = length;
    }

    private int length;
    private PositionComponent position;
    private int xCenter;
    private int yCenter;
    private ArrayList<Entity> down = new ArrayList<Entity>();
    private ArrayList<Entity> up = new ArrayList<Entity>();
    private ArrayList<Entity> left = new ArrayList<Entity>();
    private ArrayList<Entity> right = new ArrayList<Entity>();

    @Override
    public void onUpdate(double tpf) {
    }

    public boolean removeBrick(Entity entity, int x, int y, Texture texture) {
        Texture view = texture("Grass/grasspecies2.png");

        if (entity.isType(BombermanType.WALL)) {
            return false;
        } else if (entity.isType(BombermanType.BRICK)) {
            FXGL.getMasterTimer().runOnceAfter(() -> {
                entity.setType(BombermanType.GRASS);
                entity.setViewWithBBox(view);
                entity.setRenderLayer(RenderLayer.BACKGROUND);
            }, Duration.seconds(0.7));

            return false;
        } else if (x == xCenter
                && y == yCenter
                && (entity.isType(BombermanType.FLAME)
                || entity.isType(BombermanType.FLAME)
                || entity.isType(BombermanType.BOMB))) {
            entity.setViewWithBBox(texture);
            Entity flame = FXGL.getApp()
                    .getGameWorld()
                    .spawn("Flame", new SpawnData(x * Main.TILE_SIZE, y * Main.TILE_SIZE));

            if (!entity.isType(BombermanType.BOMB)) {
                FXGL.getMasterTimer().runOnceAfter(() -> {
                    entity.setViewWithBBox(view);
                }, Duration.seconds(1));
                return true;
            }
        } else if (entity.isType(BombermanType.GRASS)) {
            entity.setViewWithBBox(texture);
            Entity flame = FXGL.getApp()
                    .getGameWorld()
                    .spawn("Flame", new SpawnData(x * Main.TILE_SIZE, y * Main.TILE_SIZE));

            FXGL.getMasterTimer().runOnceAfter(() -> {
                entity.setType(BombermanType.GRASS);
                flame.getComponent(FlameControl.class).burn();
                entity.setViewWithBBox(view);
                entity.setRenderLayer(RenderLayer.BACKGROUND);
            }, Duration.seconds(1));
            return true;
        } else if (entity.isType(BombermanType.BOMBBRICK)) {
            FXGL.getMasterTimer().runOnceAfter(() -> {
                entity.setType(BombermanType.GRASS);
                entity.setViewWithBBox(view);
                entity.setRenderLayer(RenderLayer.BACKGROUND);
                FXGL.getApp()
                        .getGameWorld()
                        .spawn("bomb_item", new SpawnData(x * Main.TILE_SIZE, y * Main.TILE_SIZE));

            }, Duration.seconds(0.7));

            return false;
        } else if (entity.isType(BombermanType.FLAMEBRICK)) {
            FXGL.getMasterTimer().runOnceAfter(() -> {
                entity.setType(BombermanType.GRASS);
                entity.setViewWithBBox(view);
                entity.setRenderLayer(RenderLayer.BACKGROUND);
                FXGL.getApp()
                        .getGameWorld()
                        .spawn("flame_item", new SpawnData(x * Main.TILE_SIZE, y * Main.TILE_SIZE));

            }, Duration.seconds(0.7));

            return false;
        } else if (entity.isType(BombermanType.SPEEDITEMBRICK)) {
            FXGL.getMasterTimer().runOnceAfter(() -> {
                entity.setType(BombermanType.GRASS);
                entity.setViewWithBBox(view);
                entity.setRenderLayer(RenderLayer.BACKGROUND);
                FXGL.getApp()
                        .getGameWorld()
                        .spawn("speed_item", new SpawnData(x * Main.TILE_SIZE, y * Main.TILE_SIZE));

            }, Duration.seconds(0.7));

            return false;
        } else if (entity.isType(BombermanType.PORTALBRICK)) {
            FXGL.getMasterTimer().runOnceAfter(() -> {
                entity.setType(BombermanType.GRASS);
                entity.setViewWithBBox(view);
                entity.setRenderLayer(RenderLayer.BACKGROUND);
                FXGL.getApp()
                        .getGameWorld()
                        .spawn("portal_item", new SpawnData(x * Main.TILE_SIZE, y * Main.TILE_SIZE));

            }, Duration.seconds(0.7));

            return false;
        }
        return false;
    }

    public void removeEdge(ArrayList<Entity> dirEntitise, String direction) {
        boolean removed;
        int x, y;

        for (int i = 0; i < dirEntitise.size(); i++) {
            Entity entityDir = dirEntitise.get(i);

            Texture flameDownView = texture("Flame/flame_down1.png");
            Texture flameLeftView = texture("Flame/flame_left1.png");
            Texture flameRightView = texture("Flame/flame_right1.png");
            Texture flameUpView = texture("Flame/flame_up1.png");

            Texture flameDownTailView = texture("Flame/flame_down2.png");
            Texture flameUpTailView = texture("Flame/flame_up2.png");
            Texture flameLeftTailView = texture("Flame/flame_left2.png");
            Texture flameRightTailView = texture("Flame/flame_right2.png");


            Texture view = flameDownView;
            if (direction.equals("down")) {

                if (i == dirEntitise.size() - 1) view = flameDownTailView;
                else if (dirEntitise.get(i + 1).isType(BombermanType.WALL)) {

                    view = flameDownTailView;
                } else view = flameDownView;

            } else if (direction.equals("left")) {

                if (i == dirEntitise.size() - 1) view = flameLeftTailView;
                else if (dirEntitise.get(i + 1).isType(BombermanType.WALL)) {

                    view = flameLeftTailView;
                } else view = flameLeftView;

            } else if (direction.equals("up")) {
                if (i == dirEntitise.size() - 1) view = flameUpTailView;
                else if (dirEntitise.get(i + 1).isType(BombermanType.WALL)) {

                    view = flameUpTailView;
                } else view = flameUpView;


            } else if (direction.equals("right")) {
                if (i == dirEntitise.size() - 1) view = flameRightTailView;
                else if (dirEntitise.get(i + 1).isType(BombermanType.WALL)) {

                    view = flameRightTailView;
                } else view = flameRightView;
            }

            x = entityDir.getPositionComponent().getGridX(Main.TILE_SIZE);
            y = entityDir.getPositionComponent().getGridY(Main.TILE_SIZE);
            removed = removeBrick(entityDir, x, y, view);

            if (!removed) break;
        }
    }

    public void excRemove(List<Entity> entities, int xCenter, int yCenter, boolean width, boolean height) {
        int x, y;
        boolean removed;

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            x = entity.getPositionComponent().getGridX(Main.TILE_SIZE);
            y = entity.getPositionComponent().getGridY(Main.TILE_SIZE);

            if (y > yCenter && height) {
                down.add(entity);
            }
            if (x > xCenter && width) {
                right.add(entity);
            }
        }
        for (int i = entities.size() - 1; i >= 0; i--) {
            Entity entity = entities.get(i);
            x = entity.getPositionComponent().getGridX(Main.TILE_SIZE);
            y = entity.getPositionComponent().getGridY(Main.TILE_SIZE);

            if (y < yCenter && height) {
                up.add(entity);
            }
            if (x < xCenter && width) {
                left.add(entity);
            }
        }

        removeEdge(down, "down");

        removeEdge(left, "left");

        removeEdge(right, "right");

        removeEdge(up, "up");

        left.clear();
        right.clear();
        down.clear();
        up.clear();
        entities.clear();
    }

    public void explode(int xCenter, int yCenter) {
        play("flame.wav");
        BoundingBoxComponent bbox = getEntity().getBoundingBoxComponent();
        this.xCenter = xCenter;
        this.yCenter = yCenter;

        List<Entity> entitiesHeight = FXGL.getApp()
                .getGameWorld()
                .getEntitiesInRange(bbox.range(0, Main.TILE_SIZE * length))
                .stream()
                .filter(e -> e.isType(BombermanType.BRICK)
                        || e.isType(BombermanType.WALL)
                        || e.isType(BombermanType.GRASS)
                        || e.isType(BombermanType.SPEEDITEMBRICK)
                        || e.isType(BombermanType.BOMBBRICK)
                        || e.isType(BombermanType.FLAMEBRICK)
                        || e.isType(BombermanType.PORTALBRICK))
                .collect(Collectors.toList());

        List<Entity> entitiesWidth = FXGL.getApp()
                .getGameWorld()
                .getEntitiesInRange(bbox.range(Main.TILE_SIZE * length, 0))
                .stream()
                .filter(e -> e.isType(BombermanType.BRICK)
                        || e.isType(BombermanType.WALL)
                        || e.isType(BombermanType.GRASS)
                        || e.isType(BombermanType.SPEEDITEMBRICK)
                        || e.isType(BombermanType.BOMBBRICK)
                        || e.isType(BombermanType.FLAMEBRICK)
                        || e.isType(BombermanType.PORTALBRICK))
                .collect(Collectors.toList());

        List<Entity> entityCenter = FXGL.getApp()
                .getGameWorld()
                .getEntitiesByType(BombermanType.FLAME, BombermanType.GRASS, BombermanType.BOMB)
                .stream()
                .filter(e -> e.getPositionComponent().getGridX(Main.TILE_SIZE) == xCenter && e.getPositionComponent().getGridY(Main.TILE_SIZE) == yCenter)
                .collect(Collectors.toList());
        Texture flameCenterView = texture("Flame/flame_center.png");
        removeBrick(entityCenter.get(0), xCenter, yCenter, flameCenterView);

        excRemove(entitiesHeight, xCenter, yCenter, false, true);
        excRemove(entitiesWidth, xCenter, yCenter, true, false);
        entity.removeFromWorld();
    }
}
