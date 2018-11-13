package Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Grass extends renderMap {
    protected ImageView imageGrass = new ImageView(new Image(getClass().getResourceAsStream("/images/edit/png/grass.png")));

    public Grass(){
    }

    public Grass(int[] location){
        super(location);
        imageGrass.setLayoutX(location[0]);
        imageGrass.setLayoutY(location[1]);
    }
}
