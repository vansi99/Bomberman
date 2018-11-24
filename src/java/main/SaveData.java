package main;

import java.io.Serializable;

public class SaveData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final int highScore;

    public SaveData(String name, int highScore){
        this.highScore = highScore;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHighScore() {
        return highScore;
    }
}
