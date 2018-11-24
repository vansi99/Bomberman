package collision;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import main.BombermanType;
import main.Main;

public class PlayerEnemyHandler extends CollisionHandler {
    private Main app;

    public PlayerEnemyHandler(){
        super(BombermanType.PLAYER, BombermanType.ENEMY);
        app = (Main) FXGL.getApp();
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity enemy){
        System.out.println("collision");
        app.onPlayerKilled();
    }
}
