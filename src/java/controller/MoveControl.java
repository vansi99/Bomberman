package controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;
import main.BombermanType;

import java.util.List;

public class MoveControl extends Component {
    private PositionComponent position;
    private BoundingBoxComponent bbox;
    private LocalTimer timer = FXGL.newLocalTimer();
    public boolean going = false;


    private MoveDirection moveDir;

    public void setMoveDirection(MoveDirection moveDir) {
        this.moveDir = moveDir;
    }

    public void onAdded() {
        moveDir = FXGLMath.random(MoveDirection.values()).get();
    }

    public double speed = 0;

    @Override
    public void onUpdate(double tpf) {
        if (timer.elapsed(Duration.seconds(2))) {
            // 2. perform logic
            going = !going;
            getEntity().getComponent(MoveControl.class).setMoveDirection(FXGLMath.random(MoveDirection.values()).get());
            timer.capture();
        }

        speed = tpf * 30;

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

    public void up() {
        move(0, -5 * speed);

    }

    public void down() {

        move(0, 5 * speed);

    }

    public void left() {

        move(-5 * speed, 0);

    }

    public void right() {

        move(5 * speed, 0);
    }


    private List<Entity> walls;
    private List<Entity> bricks;

    private boolean canMove(List<Entity> entities) {
        for (int j = 0; j < entities.size(); j++) {
            if (entities.get(j).getBoundingBoxComponent().isCollidingWith(bbox)) {
                return true;
            }
        }
        return false;
    }

    private Vec2 velocity = new Vec2();

    private void move(double dx, double dy) {
        if (!getEntity().isActive())
            return;

        if (walls == null) {
            walls = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.WALL);
        }


        bricks = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.BRICK);

        velocity.set((float) dx, (float) dy);

        int length = FXGLMath.roundPositive(velocity.length());
        velocity.normalizeLocal();

        for (long i = 0; i < length; i++) {
            position.translate(velocity.x, velocity.y);

            boolean collisionBricks = canMove(bricks);
            boolean collisionWalls = canMove(walls);


            if (collisionBricks || collisionWalls) {
                position.translate(-velocity.x, -velocity.y);
                break;
            }
        }
    }
}
