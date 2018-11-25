package collision;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.Texture;
import javafx.util.Duration;
import main.BombermanType;
import main.Main;

import static com.almasb.fxgl.app.DSLKt.play;
import static com.almasb.fxgl.app.DSLKt.texture;

public class PlayerPortalItemHandler extends CollisionHandler {
    private Main app;

    public PlayerPortalItemHandler(){
        super(BombermanType.PLAYER, BombermanType.PORTAL);
        app = (Main) FXGL.getApp();
    }

    public boolean checkEnemy(){
        if(FXGL
                .getApp()
                .getGameWorld()
                .getEntitiesByType(BombermanType.ENEMY)
                .isEmpty())
            return true;
        else  return false;
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity enemy){
        if(checkEnemy()){
            play("win.wav");
            FXGL.getMasterTimer().runOnceAfter(() -> {
                app.gameOver();
            }, Duration.seconds(3));

        }
    }
}
