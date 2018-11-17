package main;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
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


public class Main extends GameApplication {

    public static final int TILE_SIZE = 60;

    private AStarGrid grid;

    public Entity getPlayer() {
        return getGameWorld().getSingleton(BombermanType.PLAYER).get();
    }

    public PlayerControl getPlayerControl() {
        return getPlayer().getComponent(PlayerControl.class);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(15 * TILE_SIZE);
        settings.setHeight(11 * TILE_SIZE);
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
    }

    @Override
    protected void initGame(){
        BombermanFactory factory = new BombermanFactory();

        getGameWorld().addEntityFactory(factory);

        TextLevelParser levelParser = new TextLevelParser(factory);

        Level level = levelParser.parse("levels/0.txt");

        getGameWorld().setLevel(level);

        getGameWorld().spawn("BG");

        grid = new AStarGrid(Main.TILE_SIZE*11, Main.TILE_SIZE*11);

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
    protected void initPhysics(){
    }

    @Override
    protected void initUI(){}

    @Override
    protected void onUpdate(double tpf){
    }


    public static void main(String[] args) {
        launch(args);
    }
}
