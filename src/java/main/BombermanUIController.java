package main;

import com.almasb.fxgl.app.listener.StateListener;
import com.almasb.fxgl.ui.ProgressBar;
import com.almasb.fxgl.ui.UIController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import com.almasb.fxgl.core.math.FXGLMath;

public class BombermanUIController implements UIController, StateListener {
    @FXML
    private Pane root;

    private ProgressBar timeBar;

    public void init(){
        timeBar = new ProgressBar(false);
        timeBar.setHeight(10);
        timeBar.setWidth(680);
        timeBar.setTranslateX(0);
        timeBar.setTranslateY(0);
//        timeBar.setFill(Color.FXMLMath.random());
    }
}
