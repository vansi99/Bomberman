package collision;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import main.BombermanType;
import main.Main;

import static com.almasb.fxgl.app.DSLKt.play;

public class FlameEnemyHandler extends CollisionHandler {

    public FlameEnemyHandler(){
        super(BombermanType.FLAME, BombermanType.ENEMY);
    }

    @Override
    protected void onCollisionBegin(Entity flame, Entity enemy){
        play("monster_die.wav");
        enemy.removeFromWorld();
    }

}
