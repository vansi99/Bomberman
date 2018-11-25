package collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import controller.PlayerControl;
import main.BombermanType;

public class PlayerBombItemHandler extends CollisionHandler {

    private boolean increasedBomb = false;


    public PlayerBombItemHandler() {
        super(BombermanType.PLAYER, BombermanType.BOMBITEM);
    }

    @Override
    protected void onCollision(Entity player, Entity item) {
        item.removeFromWorld();
        if (!increasedBomb) {
            player.getComponent(PlayerControl.class).increaseMaxBombs();
            increasedBomb = true;
        }
    }
}
