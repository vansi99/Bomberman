package Map;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class centerWall extends renderMap {
    protected ImageView imageCenterWall = new ImageView(new Image(getClass().getResourceAsStream("/images/edit/png/editwall1.png")));

    public centerWall(){
    }

    public centerWall(int[] location){
        super(location);
        imageCenterWall.setLayoutX(location[0]);
        imageCenterWall.setLayoutY(location[1]);
    }
}
