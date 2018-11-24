package controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;
import main.BombermanType;
import main.Main;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class OnealControl extends EnemyControl {
    protected PlayerControl player;

    private Texture onealUp = FXGL.getAssetLoader().loadTexture("Oneal/OnealUp.png");
    private Texture onealDown = FXGL.getAssetLoader().loadTexture("Oneal/OnealDown.png");
    private Texture onealLeft = FXGL.getAssetLoader().loadTexture("Oneal/OnealLeft.png");
    private Texture onealRight = FXGL.getAssetLoader().loadTexture("Oneal/OnealRight.png");

    @Override
    public void onUpdate(double tpf) {
        if (timer.elapsed(Duration.seconds(1.5))) {
            getEntity().getComponent(OnealControl.class).setMoveDirection(FXGLMath.random(MoveDirection.values()).get());
            timer.capture();
        }
        speed = FXGLMath.random();
        if(hasPlayer()){
            followPlayer();
        }
        else {
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
    }
    @Override
    public void up() {
        move(0, -7 * speed);
        view.setView(onealUp);
    }

    @Override
    public void down() {
        move(0, 7 * speed);
        view.setView(onealDown);
    }

    @Override
    public void left() {
        move(-7 * speed, 0);
        view.setView(onealLeft);
    }

    @Override
    public void right() {
        move(7 * speed, 0);
        view.setView(onealRight);
    }

    public boolean hasPlayer(){
        List<Entity> playerInRange = FXGL.getApp()
                .getGameWorld()
                .getEntitiesInRange(bbox.range(Main.TILE_SIZE*3, Main.TILE_SIZE*3))
                .stream()
                .filter(e -> e.isType(BombermanType.PLAYER))
                .collect(Collectors.toList());
        if(playerInRange.isEmpty()) {
            player = null;
            return false;
        }
        else {
            player = playerInRange.get(0).getComponent(PlayerControl.class);
            return true;
        }
    }

    public void followPlayer() {
        if(player.getMoveDir() == MoveDirection.UP){
            down();
        } else if(player.getMoveDir() == MoveDirection.DOWN){
            up();
        }else if(player.getMoveDir() == MoveDirection.LEFT){
            right();
        }else if(player.getMoveDir() == MoveDirection.RIGHT){
            left();
        }

    }

}
