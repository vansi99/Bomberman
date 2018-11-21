package main;

import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import controller.BombControl;
import controller.FlameControl;
import controller.PlayerControl;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


import static com.almasb.fxgl.app.DSLKt.texture;


public class BombermanFactory implements TextEntityFactory {

    @SpawnSymbol('0')
        public Entity newGrass (SpawnData data){
            return Entities.builder()
                    .from(data)
                    .type(BombermanType.GRASS)
                    .viewFromNodeWithBBox(new EntityView(new Rectangle(Main.TILE_SIZE , Main.TILE_SIZE, Color.LIGHTGREEN)))
                    .renderLayer(RenderLayer.BACKGROUND)
                    .build();
        }


    @SpawnSymbol('w')
    public Entity newWall (SpawnData data){
        return Entities.builder()
                .from(data)
                .type(BombermanType.WALL)
                .viewFromNodeWithBBox(new Rectangle(Main.TILE_SIZE, Main.TILE_SIZE, Color.GRAY.saturate()))
                .build();
    }

    @SpawnSymbol('b')
    public Entity newBrick(SpawnData data){
        return Entities.builder()
                .type(BombermanType.BRICK)
                .from(data)
                .viewFromNodeWithBBox(new Rectangle(Main.TILE_SIZE, Main.TILE_SIZE, Color.BLUE))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        Texture view = texture("Bomberman/down.png");

        return Entities.builder()
                .from(data)
                .at(0,0)
                .type(BombermanType.PLAYER)
                .bbox(new HitBox("PLAYER_BODY", new Point2D(2,2), BoundingShape.box(20,30)))
                .viewFromNode(view)
                .with(new CollidableComponent(true))
                .with(new PlayerControl())
                .build();
    }

    @Spawns("Bomb")
    public Entity newBomb(SpawnData data){
        return Entities.builder()
                .type(BombermanType.BOMB)
                .from(data)
                .viewFromNodeWithBBox(new Circle(Main.TILE_SIZE/2, Color.BLACK))
                .with(new BombControl(data.get("radius")))
                .build();
    }

    @Spawns("Flame")
    public Entity newFlame(SpawnData data){
        return Entities.builder()
                .type(BombermanType.FLAME)
                .from(data)
                .viewFromNodeWithBBox(new Rectangle(Main.TILE_SIZE, Main.TILE_SIZE, Color.RED.saturate()))
                .with(new FlameControl())
                .build();
    }

    @Override
    public char emptyChar() {
        return ' ';
    }

    @Override
    public int blockWidth() {
        return Main.TILE_SIZE;
    }

    @Override
    public int blockHeight() {
        return Main.TILE_SIZE;
    }
}
