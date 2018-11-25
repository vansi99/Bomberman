package collision;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.Texture;
import controller.PlayerControl;
import javafx.util.Duration;
import main.BombermanType;
import main.Main;

import static com.almasb.fxgl.app.DSLKt.play;
import static com.almasb.fxgl.app.DSLKt.texture;

public class PlayerEnemyHandler extends CollisionHandler {
    private Main app;

    public PlayerEnemyHandler(){
        super(BombermanType.PLAYER, BombermanType.ENEMY);
        app = (Main) FXGL.getApp();
    }

    @Override
    protected void onCollisionBegin(Entity player, Entity enemy){
        Texture deadPlayer = texture("Bomberman/ghost50.png");
        player.setView(deadPlayer);
        play("bomber_die.wav");
        FXGL.getInput().clearAll();
        FXGL.getMasterTimer().runOnceAfter(() -> {
            app.onPlayerKilled();

        }, Duration.seconds(1));
    }
}
