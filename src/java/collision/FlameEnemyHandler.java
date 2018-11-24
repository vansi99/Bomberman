package collision;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import main.BombermanType;
import main.Main;

public class FlameEnemyHandler extends CollisionHandler {

    public FlameEnemyHandler(){
        super(BombermanType.FLAME, BombermanType.ENEMY);
    }

}
