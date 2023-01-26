package game;

import game.enemies.Enemy;
import game.weapons.Weapon;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.*;


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

        primaryStage.setTitle("Dungeon RPG Indev 0.0.2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void updateGameWindow(ArrayList<Wall> walls, Weapon weapon, Enemy enemy) {
        gameWindow.clear();
        gameWindow.drawWalls(walls);
        gameWindow.drawWeapon(weapon);
        if (enemy != null)
            try {
                gameWindow.drawEnemy(enemy, new Vector2d(150, 150 - gameWindow.marginTop));
            } catch (FileNotFoundException e) {
                System.out.println("Enemy image file not found!\n" + e);
            }
    }

    public void onPlayerWalk() {
        try {
            playSoundEffect("walk");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("En error occurred with a sound effect " + e);
        }
    }
    public void onPlayerWallHit() {
        try {
            playSoundEffect("wall-hit");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("En error occurred with a sound effect " + e);
        }
    }

    private void playSoundEffect(String audioFileName) throws UnsupportedAudioFileException, LineUnavailableException,
            IOException {
        File audioFile = new File("src\\main\\resources\\soundEffects\\" + audioFileName + ".wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }
}
