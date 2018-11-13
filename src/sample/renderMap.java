package sample;

import javafx.scene.layout.Pane;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class renderMap {
    final int sizeOfStaticObject = 40;
    int margin = 30;
    int [] location;
    char [][] charOfMatrix = new char[30][30];
    int level;
    public renderMap(){
    }
    public renderMap(int[] location){
    }

    public Pane readerFileToRenderMap(String path) throws Exception{
        Pane canvas = new Pane();
        InputStream inputStream = this.getClass().getResourceAsStream(path);
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        level = Integer.parseInt(bufferedReader.readLine());
        if(level == 1) {
            String currentLine = bufferedReader.readLine();
            int rowOfMatrix = 0;
            int columeOfMatrix;
            while(currentLine != null){
                for(int i = 0; i < currentLine.length(); i++){
                    columeOfMatrix = i;
                    charOfMatrix[columeOfMatrix][rowOfMatrix] = currentLine.charAt(i);
                    if(currentLine.charAt(i) == '#'){
                        int[] location = {columeOfMatrix * sizeOfStaticObject + margin, rowOfMatrix * sizeOfStaticObject + margin};
                        Wall wall = new Wall(location);
                        canvas.getChildren().add(wall.imageWall);
                        continue;
                    }
                    if(currentLine.charAt(i) == '*'){
                        int[] location = {columeOfMatrix * sizeOfStaticObject + margin, rowOfMatrix * sizeOfStaticObject + margin};
                        Box box = new Box(location);
                        canvas.getChildren().add(box.imageBox);
                        continue;
                    }
                    if(currentLine.charAt(i) == '%'){
                        int[] location = {columeOfMatrix * sizeOfStaticObject + margin, rowOfMatrix * sizeOfStaticObject + margin};
                        centerWall centerWall = new centerWall(location);
                        canvas.getChildren().add(centerWall.imageCenterWall);
                        continue;
                    }
                    else{
                        int[] loction = {columeOfMatrix * sizeOfStaticObject + margin, rowOfMatrix * sizeOfStaticObject + margin};
                        Grass grass = new Grass(loction);
                        canvas.getChildren().add(grass.imageGrass);
                        continue;
                    }
                }
                rowOfMatrix++;
                currentLine = bufferedReader.readLine();
            }
        }
        return canvas;
    }
}
