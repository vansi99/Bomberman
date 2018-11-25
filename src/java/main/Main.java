

package main;

import collision.*;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.Level;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.extra.ai.pathfinding.AStarGrid;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.parser.text.TextLevelParser;
import com.almasb.fxgl.settings.GameSettings;
import com.almasb.fxgl.ui.UI;
import controller.PlayerControl;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.app.DSLKt.loopBGM;

import javafx.util.Duration;
import main.BombermanFactory;
import main.BombermanType;

import java.nio.file.Path;
import java.util.*;

import static com.almasb.fxgl.app.DSLKt.*;

public class Main extends GameApplication {

    public static final int TILE_SIZE = 40;

    public static final int WIDTH_SIZE = 17;

    public static final int HEIGHT_SIZE = 13;

    private static final int UI_SIZE = 10;

    //seconds
    public static final int TIME_PER_LEVEL = 240;

//    public static final int MAX_LEVELS = 3;

    private AStarGrid grid;

    public Entity getPlayer() {
        return getGameWorld().getSingleton(BombermanType.PLAYER).get();
    }

    public PlayerControl getPlayerControl() {
        return getPlayer().getComponent(PlayerControl.class);
    }

    public AStarGrid getGrid() {
        return grid;
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(TILE_SIZE * WIDTH_SIZE);
        settings.setHeight(TILE_SIZE * HEIGHT_SIZE + UI_SIZE);
        settings.setTitle("BombermanApp");
        settings.setVersion("0.8");
        settings.setIntroEnabled(false);
        settings.setMenuEnabled(false);
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void initInput(){
        getInput().addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() { getPlayerControl().up();}
        }, KeyCode.W);

        getInput().addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() { getPlayerControl().left();}
        }, KeyCode.A);

        getInput().addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() { getPlayerControl().down();}
        }, KeyCode.S);

        getInput().addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                getPlayerControl().right();
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Place Bomb") {
            @Override
            protected void onActionBegin() {
                getPlayerControl().placeBomb();

            }
        }, KeyCode.F);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("time", TIME_PER_LEVEL);
    }

    @Override
    protected  void preInit(){
        getAudioPlayer().setGlobalMusicVolume(0.2);
        getAudioPlayer().setGlobalSoundVolume(0.2);
        loopBGM("menu.wav");
    }

    @Override
    protected void initGame(){
        BombermanFactory factory = new BombermanFactory();

        getGameWorld().addEntityFactory(factory);

        TextLevelParser levelParser = new TextLevelParser(factory);

        ArrayList<String> paths = new ArrayList<String>();
        paths.add("levels/0.txt");
        paths.add("levels/1.txt");
        paths.add("levels/2.txt");
        int randomLevel = getRandom(1, 100);
        String randomMap = paths.get(randomLevel % 3);

        Level level = levelParser.parse(randomMap);
        getGameWorld().setLevel(level);

        getGameWorld().spawn("player");
        getGameWorld().spawn("Enemy", new SpawnData(40, 400));
        getGameWorld().spawn("Enemy", new SpawnData(600, 440));
        getGameWorld().spawn("Enemy", new SpawnData(600, 120));
        getGameWorld().spawn("Oneal", new SpawnData(120, 120));

        grid = new AStarGrid(Main.TILE_SIZE*12, Main.TILE_SIZE*12);

        getMasterTimer().runAtInterval(
                () -> inc("time", -1),
                Duration.seconds(1)
        );

        getGameState().<Integer>addListener("time", (old, now) -> {
            if (now == 0) {
                onPlayerKilled();
            }
        });
    }

    public int getRandom(int min, int max)
    {
        Random r = new Random();
        return min + r.nextInt(max - min);
    }
//    private int getCurrentLevel() {
//        String currentLevel = Level.;
//        int level = (int)currentLevel.charAt(0) - 48;
//        return level;
//    }

//    private void cleanupLevel(){
//        getGameWorld().getEntitiesByType(
//                BombermanType.ENEMY,
//                BombermanType.WALL,
//                BombermanType.BRICK,
//                BombermanType.BOMB,
//                BombermanType.GRASS)
//                .forEach(Entity::removeFromWorld);
//    }
//
//    private void nextLevel() {
//        getInput().setProcessInput(false);
//
//        if (geti("level") > 0) {
//            cleanupLevel();
//        }
//
//        set("enemiesKilled", 0);
//        inc("level", +1);
//
//        if (geti("level") > MAX_LEVELS) {
//            gameOver();
//            return;
//        }
//    }

    @Override
    protected void onPostUpdate(double tpf) {
        if(requestNewGame) {
            requestNewGame = false;
            getStateMachine().getPlayState().removeStateListener(uiController);
            startNewGame();
        }
    }

    @Override
    protected void initPhysics(){
        getPhysicsWorld().addCollisionHandler(new FlamePlayerHandler());
        getPhysicsWorld().addCollisionHandler(new FlameEnemyHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerEnemyHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerSpeedItemHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerBombItemHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerFlameItemHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerPortalItemHandler());
    }

    private BombermanUIController uiController;

    @Override
    protected void initUI(){
        uiController = new BombermanUIController();
        getStateMachine().getPlayState().addStateListener(uiController);

        UI ui = getAssetLoader().loadUI("ui_bomberman.fxml", uiController);
        ui.getRoot().setTranslateY(HEIGHT_SIZE * TILE_SIZE);

        getGameScene().addUI(ui);
    }

    private boolean requestNewGame = false;

    public void onPlayerKilled(){
        requestNewGame = true;
    }

    public void gameOver(){
        getDisplay().showMessageBox("Game Over. Press OK to exit", this::exit);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
