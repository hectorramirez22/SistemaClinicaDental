package dev.example.sistemaclinicadental;

import javafx.stage.Stage;

public class StageManager {
    private static StageManager instance;
    private Stage primaryStage;

    private StageManager() {
    }

    public static StageManager getInstance() {
        if (instance == null) {
            instance = new StageManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void changeTitle(String newTitle) {
        primaryStage.setTitle(newTitle);
    }
}
