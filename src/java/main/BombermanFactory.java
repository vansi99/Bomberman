package main;


import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import controller.*;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.app.DSLKt.play;
import static com.almasb.fxgl.app.DSLKt.texture;


public class BombermanFactory implements TextEntityFactory {

    @SpawnSymbol('0')
        public Entity newGrass (SpawnData data){
        Texture view = texture("Grass/grasspecies2.png");

            return Entities.builder()
                    .from(data)
                    .type(BombermanType.GRASS)
                    .bbox(new HitBox("GRASS", new Point2D(0, 0), BoundingShape.box(40, 40)))
                    .viewFromNode(view)
                    .renderLayer(RenderLayer.BACKGROUND)
                    .build();
        }

    @SpawnSymbol('w')
    public Entity newWall (SpawnData data){
        Texture view = texture("Wall/editwall2.png");

        return Entities.builder()
                .from(data)
                .type(BombermanType.WALL)
                .bbox(new HitBox("WALL", new Point2D(0, 0), BoundingShape.box(40, 40)))
                .viewFromNode(view)
                .build();
    }

    @SpawnSymbol('b')
    public Entity newBrick(SpawnData data){
        Texture view = texture("Brick/editbrick.png");

        return Entities.builder()
                .type(BombermanType.BRICK)
                .from(data)
                .bbox(new HitBox("BRICK", new Point2D(0,0), BoundingShape.box(40,40)))
                .viewFromNode(view)
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        Texture view = texture("Bomberman/down.png");

        return Entities.builder()
                .from(data)
                .at(40,40)
                .type(BombermanType.PLAYER)
                .bbox(new HitBox("PLAYER_BODY", new Point2D(2, 2), BoundingShape.box(35,35)))
                .viewFromNode(view)
                .with(new CollidableComponent(true))
                .with(new PlayerControl())
                .build();
    }

    @Spawns("Bomb")
    public Entity newBomb(SpawnData data){
        play("new_bomb.wav");
        Texture view = texture("Bomb/bomb1.png");

        return Entities.builder()
                .type(BombermanType.BOMB)
                .from(data)
                .bbox(new HitBox("BOMB", new Point2D(0, 0), BoundingShape.box(40, 40)))
                .viewFromNode(view)
                .with(new BombControl(data.get("radius")))
                .build();
    }

    @Spawns("Flame")
    public Entity newFlame(SpawnData data){
        return Entities.builder()
                .from(data)
                .type(BombermanType.FLAME)
                .bbox(new HitBox("FLAME", new Point2D(0, 0), BoundingShape.box(40, 40)))
                .with(new CollidableComponent(true))
                .with(new FlameControl())
                .build();
    }

    @Spawns("Enemy")
    public Entity newEnemy (SpawnData data){
        Texture view = texture("Enemy/EnemyDown2.png");

        Entity enemy = Entities.builder()
                        .from(data)
                        .type(BombermanType.ENEMY)
                        .at(200, 120)
                        .bbox(new HitBox("ENEMY_BODY", new Point2D(2, 2), BoundingShape.box(35, 35)))
                        .viewFromNode(view)
                        .with(new CollidableComponent(true))
                        .with(new EnemyControl())
                        .build();
        return enemy;
    }

    @Spawns("Oneal")
    public Entity newOneal(SpawnData data) {
        Texture view = texture("Oneal/OnealDown.png");

        Entity oneal = Entities.builder()
                        .from(data)
                        .type(BombermanType.ENEMY)
                        .at(80,120)
                        .bbox(new HitBox("ONEAL_BODY", new Point2D(2,2), BoundingShape.box(35,35)))
                        .viewFromNode(view)
                        .with(new CollidableComponent(true))
                        .with(new OnealControl())
                        .build();
        return oneal;
    }

    @Spawns("")

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
