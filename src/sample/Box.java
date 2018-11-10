package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Box extends renderMap{
    protected ImageView imageBox = new ImageView(new Image(getClass().getResourceAsStream("/images/edit/png/box40.png")));
    public Box(){
    }

    public Box(int[] location){
        super(location);
        imageBox.setLayoutX(location[0]);
        imageBox.setLayoutY(location[1]);
    }
}
