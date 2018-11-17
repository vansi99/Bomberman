package main;

import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.view.EntityView;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import controller.PlayerControl;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import main.BombermanType;

import static com.almasb.fxgl.app.DSLKt.texture;


public class BombermanFactory implements TextEntityFactory {
    @Spawns("BG")
    public Entity newBacground(SpawnData data){
        return Entities.builder()
                .at(0,0)
                .viewFromNodeWithBBox(new EntityView(new Rectangle(15* main.Main.TILE_SIZE , 11* main.Main.TILE_SIZE, Color.LIGHTGREEN)))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    @SpawnSymbol('w')
    public Entity newWall (SpawnData data){
        return Entities.builder()
                .from(data)
                .type(BombermanType.WALL)
                .viewFromNodeWithBBox(new Rectangle(40, 40, Color.GRAY.saturate()))

                .build();
    }

    @SpawnSymbol('1')
    public Entity newPlayer(SpawnData data){
        Texture view = texture("Bomberman/down.png");

        return Entities.builder()
                .from(data)
                .type(BombermanType.PLAYER)
                .bbox(new HitBox("PLAYER_BODY", new Point2D(2,2), BoundingShape.box(40,60)))
                .viewFromNode(view)
                .with(new CollidableComponent(true))
                .with(new PlayerControl())
                .build();
    }

//    @Spawns("Bomb")
//    public Entity newBomb(SpawnData data){
//        return Entities.builder()
//                .type(BombermanType.BOMB)
//                .from(data)
//                .viewFromNodeWithBBox(new Circle(Main.TILE_SIZE/2), Color.BLACK)
//                .with(ne)
//    }

    @Override
    public char emptyChar() {
        return ' ';
    }

    @Override
    public int blockWidth() {
        return 60;
    }

    @Override
    public int blockHeight() {
        return 60;
    }
}
