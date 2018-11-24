package main;

import collision.FlameEnemyHandler;
import collision.FlamePlayerHandler;
import collision.PlayerEnemyHandler;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.Level;
import com.almasb.fxgl.extra.ai.pathfinding.AStarGrid;
import com.almasb.fxgl.extra.ai.pathfinding.NodeState;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.parser.text.TextLevelParser;
import com.almasb.fxgl.settings.GameSettings;
import controller.PlayerControl;
import javafx.scene.input.KeyCode;

import main.BombermanFactory;
import main.BombermanType;

import static com.almasb.fxgl.app.DSLKt.loopBGM;

public class Main extends GameApplication {

    public static final int TILE_SIZE = 40;

    public static final int WIDTH_SIZE = 17;

    public static final int HEIGHT_SIZE = 13;

    private static final int UI_SIZE = 10;

    //seconds
    public static final int TIME_PER_LEVEL = 240;

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
        settings.setVersion("0.1");
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
            protected void onAction() {
                getPlayerControl().placeBomb();

            }
        }, KeyCode.F);
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

        Level level = levelParser.parse("levels/0.txt");

        getGameWorld().setLevel(level);

        getGameWorld().spawn("player");
        getGameWorld().spawn("Enemy");
        getGameWorld().spawn("Oneal");

        grid = new AStarGrid(Main.TILE_SIZE*12, Main.TILE_SIZE*12);

        getGameWorld().getEntitiesByType(BombermanType.WALL)
                .stream()
                .map(Entity::getPosition)
                .forEach(point -> {
                    int x = (int) point.getX() / TILE_SIZE;
                    int y = (int) point.getY() / TILE_SIZE;

                    grid.setNodeState(x, y, NodeState.NOT_WALKABLE);
                });
    }

    @Override
    protected void onPostUpdate(double tpf) {
        if(requestNewGame) {
            requestNewGame = false;
            startNewGame();
        }
    }

    @Override
    protected void initPhysics(){
        //getPhysicsWorld().addCollisionHandler(new FlamePlayerHandler());
        getPhysicsWorld().addCollisionHandler(new FlameEnemyHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerEnemyHandler());
    }

    @Override
    protected void initUI(){}


    private boolean requestNewGame = false;

    public void onPlayerKilled(){
        requestNewGame = true;
    }

    private void gameOver(){
        getDisplay().showMessageBox("Game Over. Press OK to exit", this::exit);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
