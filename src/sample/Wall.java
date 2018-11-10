package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Wall extends renderMap {
    protected ImageView imageWall = new ImageView(new Image(getClass().getResourceAsStream("/images/edit/png/wall40.png")));

    public Wall(){
    }

    public Wall(int [] location){
        super(location);
        imageWall.setLayoutX(location[0]);
        imageWall.setLayoutY(location[1]);
    }
}
