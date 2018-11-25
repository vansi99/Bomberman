package controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import main.BombermanType;
import main.Main;

import java.util.List;


public class PlayerControl extends Component {
    private PositionComponent position;
    private int maxBombs = 100;
    private int bombsPlaced = 0;
    private int bombSize = 1;
    private boolean canMoveBomb = true;
    private BoundingBoxComponent bbox;
    private ViewComponent view;

    private MoveDirection moveDir = MoveDirection.UP;

    private double speed = 0.3;

    private Texture textureDown = FXGL.getAssetLoader().loadTexture("Bomberman/down55.png");
    private Texture textureUp = FXGL.getAssetLoader().loadTexture("Bomberman/up55.png");
    private Texture textureLeft = FXGL.getAssetLoader().loadTexture("Bomberman/left55.png");
    private Texture textureRight = FXGL.getAssetLoader().loadTexture("Bomberman/right55.png");

    @Override
    public void onUpdate(double tpf) {
    }

    public void increaseSpeed() { this.speed = this.speed * 1.1; }

    public MoveDirection getMoveDir() {
        return moveDir;
    }

    public void increaseMaxBombs() {
        maxBombs++;
    }

    public void increaseBombSize() {
        bombSize++;
    }

    public void placeBomb() {

        if (bombsPlaced == maxBombs) {
            return;
        }
        bombsPlaced++;

        int x = position.getGridX(Main.TILE_SIZE);
        int y = position.getGridY(Main.TILE_SIZE);
        canMoveBomb = true;

        Entity bomb = FXGL.getApp()
                .getGameWorld()
                .spawn("Bomb", new SpawnData(x * Main.TILE_SIZE, y * Main.TILE_SIZE));
        bomb.getComponent(BombControl.class).setLength(this.bombSize);
        FXGL.getMasterTimer().runOnceAfter(() -> {
            bomb.getComponent(BombControl.class).explode(x, y);
            bombsPlaced--;
        }, Duration.seconds(4));

    }


    public void up() {
        moveDir = MoveDirection.UP;

        move(0, -5 * speed);

        view.setView(textureUp);
    }

    public void down() {
        moveDir = MoveDirection.DOWN;

        move(0, 5 * speed);
        view.setView(textureDown);
    }

    public void left() {
        moveDir = MoveDirection.LEFT;

        move(-5 * speed, 0);
        view.setView(textureLeft);
    }


    public void right() {
        moveDir = MoveDirection.RIGHT;

        move(5 * speed, 0);
        view.setView(textureRight);
    }


    private List<Entity> walls;
    private List<Entity> bricks;
    private List<Entity> SpeedBricks;
    private List<Entity> BombBricks;
    private List<Entity> FlameBricks;
    private List<Entity> Bombs;


    private boolean canMove(List<Entity> entities) {
        for (int j = 0; j < entities.size(); j++) {
            if(entities.get(j).isType(BombermanType.BOMB)){
                if (!entities.get(j).getBoundingBoxComponent().isCollidingWith(bbox) && canMoveBomb){
                    canMoveBomb = false;
                } else if(!canMoveBomb && entities.get(j).getBoundingBoxComponent().isCollidingWith(bbox)) return true;
            } else if (!entities.get(j).isType(BombermanType.BOMB) && entities.get(j).getBoundingBoxComponent().isCollidingWith(bbox)) return true;
        }
        return false;
    }

    private void move(double dx, double dy) {
        if (!getEntity().isActive())
            return;

        if (walls == null) {
            walls = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.WALL);
        }

        bricks = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.BRICK);
        SpeedBricks = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.SPEEDITEMBRICK);
        BombBricks = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.BOMBBRICK);
        FlameBricks = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.FLAMEBRICK);
        Bombs = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.BOMB);

        double mag = Math.sqrt(dx * dx + dy * dy);
        long length = Math.round(mag);

        double unitX = dx / mag;
        double unitY = dy / mag;

        for (long i = 0; i < length; i++) {
            position.translate(unitX, unitY);

            boolean collisionBricks = canMove(bricks);
            boolean collisionWalls = canMove(walls);
            boolean collisionSpeedBricks = canMove(SpeedBricks);
            boolean collisionBombBricks = canMove(BombBricks);
            boolean collisionFlameBricks = canMove(FlameBricks);
            boolean collisionBombs = canMove(Bombs);

            if (collisionBricks
                    || collisionWalls
                    || collisionSpeedBricks
                    || collisionBombBricks
                    || collisionFlameBricks
                    || collisionBombs) {
                position.translate(-unitX, -unitY);
                break;
            }
        }
    }
}
