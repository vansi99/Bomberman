package ai;

import com.almasb.fxgl.ai.GoalAction;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.extra.ai.pathfinding.NodeState;
import com.almasb.fxgl.extra.entity.components.RandomMoveComponent;
import com.almasb.fxgl.time.LocalTimer;
import controller.MoveControl;
import controller.MoveDirection;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import main.BombermanType;
import main.Main;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class RandomMoveAction extends GoalAction {

    private LocalTimer timer = FXGL.newLocalTimer();

    @Override
    public void start() {
        timer.capture();
        getEntity().getComponent(MoveControl.class).setMoveDirection(FXGLMath.random(MoveDirection.values()).get());
    }

    @Override
    public boolean reachedGoal() {
        return timer.elapsed(Duration.seconds(2));
    }

    @Override
    public void end() {
        FXGL.<Main>getAppCast()
                .getGrid()
                .getNodes()
                .stream()
                .filter(n -> n.getState() == NodeState.WALKABLE)
                .sorted(Comparator.comparingDouble(n -> getEntity().getPosition().distance(n.getX() * Main.TILE_SIZE, n.getY() * Main.TILE_SIZE)))
                .findFirst()
                .ifPresent(n -> {
                    getEntity().setPosition(n.getX() * Main.TILE_SIZE, n.getY() * Main.TILE_SIZE);
                });
    }

    @Override
    public void onUpdate(double tpf) {

    }
}

