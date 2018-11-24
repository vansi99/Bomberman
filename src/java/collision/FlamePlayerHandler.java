package collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import main.BombermanType;
import main.Main;


public class FlamePlayerHandler extends CollisionHandler {
    private Main main;

    public FlamePlayerHandler(){
        super(BombermanType.FLAME, BombermanType.PLAYER);
    }


    @Override
    protected void onCollisionBegin(Entity flame, Entity player){
        main.onPlayerKilled();
    }

}
