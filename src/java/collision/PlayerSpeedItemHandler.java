package collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import controller.PlayerControl;
import main.BombermanType;

import static com.almasb.fxgl.app.DSLKt.play;

public class PlayerSpeedItemHandler extends CollisionHandler {
    private boolean increasedSpeed = false;

    public PlayerSpeedItemHandler(){
        super(BombermanType.PLAYER, BombermanType.SPEEDITEM);
    }

    @Override
    protected void onCollision(Entity player, Entity item) {
        PlayerControl control = player.getComponent(PlayerControl.class);
        item.removeFromWorld();
        increasedSpeed = false;
        if (!increasedSpeed) {
            control.increaseSpeed();
            play("items.wav");
            increasedSpeed = true;
        }
    }
}
