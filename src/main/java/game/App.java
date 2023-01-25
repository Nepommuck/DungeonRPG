package game;

import game.weapon.Weapon;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class App extends Application {
    private GameEngine gameEngine;
    private GameWindow gameWindow;

    @Override
    public void start(Stage primaryStage) throws IOException {
        final int boxSize = 800;
        final int marginTop = 50, marginBottom = 50;
        final int width = boxSize, height = boxSize - (marginTop + marginBottom);

        gameWindow = new GameWindow(boxSize, marginTop, marginBottom);
        gameEngine = new GameEngine(this,
            new Map("maps\\trial_map.txt")
        );

        Button forwardButton = new Button("FORWARD");
        forwardButton.setOnAction(e -> {
            gameEngine.movePlayer(MoveDirection.FORWARD);
        });
        Button rightButton = new Button("R");
        rightButton.setOnAction(e -> {
            gameEngine.rotatePlayer(true);
        });
        Button leftButton = new Button("L");
        leftButton.setOnAction(e -> {
            gameEngine.rotatePlayer(false);
        });

        HBox box = new HBox(
                gameWindow,
                leftButton,
                forwardButton,
                rightButton
        );
        Scene scene = new Scene(
                box,
                1000, 800
        );

        primaryStage.setTitle("Dungeon RPG Indev 0.0.1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void updateGameWindow(ArrayList<Wall> walls, Weapon weapon) {
        gameWindow.clear();
        gameWindow.drawWalls(walls);
        gameWindow.drawWeapon(weapon);
    }
}
