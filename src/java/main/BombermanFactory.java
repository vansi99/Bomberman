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
                .bbox(new HitBox("WALL", new Point2D(0, 0), BoundingShape.box(40, 30)))
                .viewFromNode(view)
                .build();
    }

    @SpawnSymbol('b')
    public Entity newBrick(SpawnData data){
        Texture view = texture("Brick/editbrick.png");

        return Entities.builder()
                .type(BombermanType.BRICK)
                .from(data)
                .bbox(new HitBox("BRICK", new Point2D(0,0), BoundingShape.box(40,30)))
                .viewFromNode(view)
                .build();
    }

    @SpawnSymbol('s')
    public Entity newItemSpeed(SpawnData data){
        Texture view = texture("Brick/editbrick.png");

        return Entities.builder()
                .type(BombermanType.SPEEDITEMBRICK)
                .from(data)
                .bbox(new HitBox("Speed_Item", new Point2D(0,0), BoundingShape.box(40,30)))
                .viewFromNode(view)
                .build();
    }

    @SpawnSymbol('i')
    public Entity newItemBomb(SpawnData data){
        Texture view = texture("Brick/editbrick.png");

        return Entities.builder()
                .type(BombermanType.BOMBBRICK)
                .from(data)
                .bbox(new HitBox("IncreaseBomb_Item", new Point2D(0,0), BoundingShape.box(40,30)))
                .viewFromNode(view)
                .build();
    }

    @SpawnSymbol('f')
    public Entity newItemFlame(SpawnData data){
        Texture view = texture("Brick/editbrick.png");

        return Entities.builder()
                .type(BombermanType.FLAMEBRICK)
                .from(data)
                .bbox(new HitBox("IncreaseFlame_Item", new Point2D(0,0), BoundingShape.box(40,30)))
                .viewFromNode(view)
                .build();
    }

    @SpawnSymbol('k')
    public Entity newPortal(SpawnData data){
        Texture view = texture("Brick/editbrick.png");

        return Entities.builder()
                .type(BombermanType.PORTALBRICK)
                .from(data)
                .bbox(new HitBox("NewPortal_Item", new Point2D(0,0), BoundingShape.box(40,30)))
                .viewFromNode(view)
                .build();
    }

    @Spawns("speed_item")
    public Entity newSpeedItem(SpawnData data){
        Texture view = texture("Item/item_shoe.png");
        return Entities.builder()
                .from(data)
                .type(BombermanType.SPEEDITEM)
                .bbox(new HitBox("SPEED_ITEM", new Point2D(2,2),BoundingShape.box(40,30)))
                .viewFromNode(view)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("bomb_item")
    public Entity newBombItem(SpawnData data){
        Texture view = texture("Item/item_bomb.png");
        return Entities.builder()
                .from(data)
                .type(BombermanType.BOMBITEM)
                .bbox(new HitBox("BOMB_ITEM", new Point2D(2,2),BoundingShape.box(40,30)))
                .viewFromNode(view)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("flame_item")
    public Entity newFlameItem(SpawnData data){
        Texture view = texture("Item/item_bombsize.png");
        return Entities.builder()
                .from(data)
                .type(BombermanType.FLAMEITEM)
                .bbox(new HitBox("FLAME_ITEM", new Point2D(2,2),BoundingShape.box(40,30)))
                .viewFromNode(view)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("portal_item")
    public Entity newPortalItem(SpawnData data) {
        Texture view = texture("Portal/key.png");
        return Entities.builder()
                .from(data)
                .type(BombermanType.PORTAL)
                .bbox(new HitBox("PORTAL_ITEM", new Point2D(2,2), BoundingShape.box(40,30)))
                .viewFromNode(view)
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        Texture view = texture("Bomberman/down.png");

        return Entities.builder()
                .from(data)
                .at(40,40)
                .type(BombermanType.PLAYER)
                .bbox(new HitBox("PLAYER_BODY", new Point2D(2, 2), BoundingShape.box(35,40)))
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
                .bbox(new HitBox("BOMB", new Point2D(0, 0), BoundingShape.box(40, 30)))
                .viewFromNode(view)
                .with(new CollidableComponent(true))
                .with(new BombControl())
                .build();
    }

    @Spawns("Flame")
    public Entity newFlame(SpawnData data){
        return Entities.builder()
                .from(data)
                .type(BombermanType.FLAME)
                .bbox(new HitBox("FLAME", new Point2D(0, 0), BoundingShape.box(35, 38)))
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
                        .bbox(new HitBox("ENEMY_BODY", new Point2D(2, 2), BoundingShape.box(38, 40)))
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
                        .at(data.getX(),data.getY())
                        .bbox(new HitBox("ONEAL_BODY", new Point2D(2,2), BoundingShape.box(37,38)))
                        .viewFromNode(view)
                        .with(new CollidableComponent(true))
                        .with(new OnealControl())
                        .build();
        return oneal;
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
