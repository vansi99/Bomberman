package controller;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import main.BombermanType;
import main.Main;

import java.util.List;


public class PlayerControl extends Component {
    private PositionComponent position;
    private int maxBombs = 1;
    private int bombsPlaced = 0;
    private BoundingBoxComponent bbox;
    private ViewComponent view;

    private double speed = 0;

    private Texture textureDown = FXGL.getAssetLoader().loadTexture("Bomberman/down.png");
    private Texture textureUp = FXGL.getAssetLoader().loadTexture("Bomberman/up.png");
    private Texture textureLeft = FXGL.getAssetLoader().loadTexture("Bomberman/left.png");
    private Texture textureRight = FXGL.getAssetLoader().loadTexture("Bomberman/right.png");

    @Override
    public void onUpdate( double tpf){
        speed = tpf * 15;

        if (position.getX() < 0) {
            position.setX(Main.TILE_SIZE * 11 - bbox.getWidth() - 5);
        }

        if (bbox.getMaxXWorld() >  Main.TILE_SIZE * 15) {
            position.setX(0);
        }
    }

    public void increaseMaxBombs() {
        maxBombs ++;
    }

    public void placeBomb() {
        if(bombsPlaced == maxBombs){
            return;
        }
    }


    public void up() {

        move(0, -5*speed);

        view.setView(textureUp);
    }

    public void down() {

        move(0, 5*speed);
        view.setView(textureDown);
    }

    public void left() {

        move(-5*speed, 0);
        view.setView(textureLeft);
    }

    public void right() {

        move(5*speed, 0);
        view.setView(textureRight);
    }


    private List<Entity> walls;

    private void move(double dx, double dy) {
        if (!getEntity().isActive())
            return;

        if(walls == null){
            walls = FXGL.getApp().getGameWorld().getEntitiesByType(BombermanType.WALL);
        }

        double mag = Math.sqrt(dx * dx + dy * dy);
        long length = Math.round(mag);


        double unitX = dx / mag;
        double unitY = dy / mag;

        for (long i = 0; i < length; i++) {
            position.translate(unitX,unitY);

            boolean collision = false;

            for (int j = 0; j < walls.size(); j++) {
                if (walls.get(j).getBoundingBoxComponent().isCollidingWith(bbox)) {
                    collision = true;
                    break;
                }
            }

            if (collision) {
                position.translate(-unitX, -unitY);
                break;
            }
        }
    }
}