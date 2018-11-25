package collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import controller.PlayerControl;
import main.BombermanType;

public class PlayerFlameItemHandler extends CollisionHandler {
    private boolean increasedBombSize = false;


    public PlayerFlameItemHandler() {
        super(BombermanType.PLAYER, BombermanType.FLAMEITEM);
    }

    @Override
    protected void onCollision(Entity player, Entity item) {
        PlayerControl control = player.getComponent(PlayerControl.class);
        item.removeFromWorld();
        if(!increasedBombSize) {
            control.increaseBombSize();
            increasedBombSize = true;
        }
    }
}
