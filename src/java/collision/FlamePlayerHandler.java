package collision;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;
import main.BombermanType;
import main.Main;

import static com.almasb.fxgl.app.DSLKt.texture;


public class FlamePlayerHandler extends CollisionHandler {
    private Main app;

    public FlamePlayerHandler(){
        super(BombermanType.FLAME, BombermanType.PLAYER);
        app = (Main) FXGL.getApp();
    }


    @Override
    protected void onCollisionBegin(Entity flame, Entity player){
        Texture deadPlayer = texture("Bomberman/bomber_dead.png");
        player.setView(deadPlayer);

        FXGL.getMasterTimer().runOnceAfter(() -> {
            app.onPlayerKilled();
        }, Duration.seconds(1));

    }

}
