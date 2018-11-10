package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Pane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Bomberman");
        renderMap render = new renderMap();
//        System.out.println(render.readerFileToRenderMap("Map.txt"));
        root.getChildren().add(render.readerFileToRenderMap("Map.txt"));
        primaryStage.setScene(new Scene(root, 900, 650));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
