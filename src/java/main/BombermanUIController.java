package main;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.listener.StateListener;
import com.almasb.fxgl.scene.CSS;
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

        timeBar.getStylesheets().add(getClass().getResource("/assets/ui/css/style.css").toExternalForm());
        timeBar.getStyleClass().add("progress-bar");

        timeBar.setHeight(10);
        timeBar.setWidth(680);
        timeBar.setTranslateX(0);
        timeBar.setTranslateY(0);
        timeBar.setFill(Color.RED);
        timeBar.setBackgroundFill(Color.BLACK);

        timeBar.setId("timeBar");



        timeBar.setMaxValue(Main.TIME_PER_LEVEL);
        timeBar.setMinValue(0);
        timeBar.setLabelVisible(false);
        timeBar.setCurrentValue(Main.TIME_PER_LEVEL);
        timeBar.currentValueProperty().bind(FXGL.getApp().getGameState().intProperty("time"));


        root.getChildren().addAll(timeBar);
    }

    @Override
    public void onUpdate(double tpf){

    }
}
