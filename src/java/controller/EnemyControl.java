package controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;
import main.BombermanType;
import main.Main;

import java.util.List;
import java.util.stream.Collectors;

import static com.almasb.fxgl.app.DSLKt.texture;

public class EnemyControl extends Component {
    protected PositionComponent position;
    protected BoundingBoxComponent bbox;
    protected LocalTimer timer = FXGL.newLocalTimer();
    protected ViewComponent view;

    protected MoveDirection moveDir;

    private Texture enemyUp = FXGL.getAssetLoader().loadTexture("Enemy/EnemyUp2.png");
    private Texture enemyDown = FXGL.getAssetLoader().loadTexture("Enemy/EnemyDown2.png");
    private Texture enemyLeft = FXGL.getAssetLoader().loadTexture("Enemy/EnemyLeft2.png");
    private Texture enemyRight = FXGL.getAssetLoader().loadTexture("Enemy/EnemyRight2.png");

    public void setMoveDirection(MoveDirection moveDir) {
        this.moveDir = moveDir;
    }

    public void onAdded() {
        moveDir = FXGLMath.random(MoveDirection.values()).get();
    }

    public double speed = 0;

    @Override
    public void onUpdate(double tpf) {
        if (timer.elapsed(Duration.seconds(1.5))) {
            getEntity().getComponent(EnemyControl.class).setMoveDirection(FXGLMath.random(MoveDirection.values()).get());
            timer.capture();
        }

        speed = tpf * 40;
        randomMove();
    }

    public void up() {
        move(0, -5 * speed);
        view.setView(enemyUp);
    }

    public void down() {
        move(0, 5 * speed);
        view.setView(enemyDown);
    }

    public void left() {
        move(-5 * speed, 0);
        view.setView(enemyLeft);
    }

    public void right() {
        move(5 * speed, 0);
        view.setView(enemyRight);
    }

    protected void randomMove(){
        switch (moveDir) {
            case UP:
                up();
                break;

            case DOWN:
                down();
                break;

            case LEFT:
                left();
                break;

            case RIGHT:
                right();
                break;
        }
    }

    protected List<Entity> walls;
    private List<Entity> bricks;
    private List<Entity> SpeedBricks;
    private List<Entity> BombBricks;
    private List<Entity> FlameBricks;

    protected boolean canMove(List<Entity> entities) {
        for (int j = 0; j < entities.size(); j++) {
            if (entities.get(j).getBoundingBoxComponent().isCollidingWith(bbox)) {
                return true;
            }
        }
        return false;
    }

    protected Vec2 velocity = new Vec2();

    protected void move(double dx, double dy) {
        if (!getEntity().isActive())
            return;

        if (walls == null) {
            walls = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.WALL);
        }


        bricks = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.BRICK);
        SpeedBricks = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.SPEEDITEMBRICK);
        BombBricks = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.BOMBBRICK);
        FlameBricks = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.FLAMEBRICK);

        velocity.set((float) dx, (float) dy);

        int length = FXGLMath.roundPositive(velocity.length());
        velocity.normalizeLocal();

        for (long i = 0; i < length; i++) {
            position.translate(velocity.x, velocity.y);

            boolean collisionBricks = canMove(bricks);
            boolean collisionWalls = canMove(walls);
            boolean collisionSpeedBricks = canMove(SpeedBricks);
            boolean collisionBombBricks = canMove(BombBricks);
            boolean collisionFlameBricks = canMove(FlameBricks);


            if (collisionBricks
                    || collisionWalls
                    || collisionSpeedBricks
                    || collisionBombBricks
                    || collisionFlameBricks) {
                position.translate(-velocity.x, -velocity.y);
                break;
            }
        }
    }

}
