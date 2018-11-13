package Map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Brick extends renderMap {
    protected ImageView imageBrick = new ImageView(new Image(getClass().getResourceAsStream("/images/edit/png/editbrick.png")));
    public Brick(){
    }

    public Brick(int[] location){
        super(location);
        imageBrick.setLayoutX(location[0]);
        imageBrick.setLayoutY(location[1]);
    }
}
