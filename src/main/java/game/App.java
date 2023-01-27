package game;

import game.enemies.Enemy;
import game.weapons.Weapon;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.*;


public class App extends Application {
    private GameEngine gameEngine;
    private GameWindow gameWindow;
    private final BorderPane rightPanel = new BorderPane();
    private final HBox fightInfoBox = new HBox();
    private final VBox fightLog = new VBox();
    private final HBox playerInfoBox = new HBox();

    @Override
    public void start(Stage primaryStage) throws IOException {
        final int boxSize = 800;
        final int marginTop = 50, marginBottom = 50;

        gameWindow = new GameWindow(boxSize, marginTop, marginBottom);
        gameEngine = new GameEngine(this,
            new Map("maps\\trial_map.txt")
        );

        rightPanel.setPadding(new Insets(20, 20, 20, 20));
        rightPanel.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        rightPanel.setMinWidth(350);

        updatePlayerInfo(gameEngine.getPlayer());
        setPanelToWalkingMode();
        HBox mainBox = new HBox(gameWindow, rightPanel);
        Scene scene = new Scene(mainBox, boxSize + 260, boxSize - (marginTop + marginBottom));

        primaryStage.setTitle("Dungeon RPG Indev 0.0.3");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    private Button createButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setFont(Font.font("Courier New", FontWeight.BOLD, 36));
        button.setOnAction(eventHandler);
        return button;
    }

    public void setPanelToWalkingMode() {
        Button forwardButton = createButton("^", e -> {
            gameEngine.movePlayer(MoveDirection.FORWARD);
        });
        Button backwardButton = createButton("V", e -> {
            gameEngine.movePlayer(MoveDirection.BACKWARD);
        });
        Button goLeftButton = createButton("<", e -> {
            gameEngine.movePlayer(MoveDirection.LEFT);
        });
        Button goRightButton = createButton(">", e -> {
            gameEngine.movePlayer(MoveDirection.RIGHT);
        });
        Button rotateLeftButton = createButton("L", e -> {
            gameEngine.rotatePlayer(false);
        });
        Button rotateRightButton = createButton("R", e -> {
            gameEngine.rotatePlayer(true);
        });

        GridPane buttonGrid = new GridPane();
        buttonGrid.setHgap(5);
        buttonGrid.setVgap(5);
        buttonGrid.setMaxHeight(200);

        buttonGrid.add(rotateLeftButton, 0, 0);
        buttonGrid.add(forwardButton, 1, 0);
        buttonGrid.add(rotateRightButton, 2, 0);
        buttonGrid.add(goLeftButton, 0, 1);
        buttonGrid.add(backwardButton, 1, 1);
        buttonGrid.add(goRightButton, 2, 1);

        rightPanel.getChildren().clear();
        rightPanel.setBottom(
                new VBox(buttonGrid, playerInfoBox)
        );
    }

    public void setPanelToFightingMode(Enemy enemy) {
        clearFightLog();
        try {
            playSoundEffect("fight-begins");
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("En error occurred with a sound effect " + e);
        }
        Button attackButton = createButton("ATTACK", e -> {
            gameEngine.attackEnemy();
        });

        GridPane buttonGrid = new GridPane();
        buttonGrid.add(attackButton, 0, 0);

        updateEnemyInfo(enemy);
        rightPanel.getChildren().clear();
        rightPanel.setTop(
                new VBox(fightInfoBox, fightLog)
        );
        rightPanel.setBottom(
                new VBox(buttonGrid, playerInfoBox)
        );
    }
    private void updateEnemyInfo(Enemy enemy) {
        Label enemyLabel = new Label(enemy.toString() + "\n" + enemy.getHealthPoints() + "HP");
        enemyLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 30));
        fightInfoBox.getChildren().clear();
        fightInfoBox.getChildren().add(enemyLabel);
    }
    private void updatePlayerInfo(Player player) {
        Label playerLabel = new Label("Player:\n" +
                player.getHealthPoints() + "HP / " + player.getMaxHealthPoints() + "HP");
        playerLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 30));
        playerInfoBox.getChildren().clear();
        playerInfoBox.getChildren().add(playerLabel);
    }
    private void updateFightLog(String message) {
        fightLog.getChildren().add(0, new Label(message));
    }
    private void clearFightLog() {
        fightLog.getChildren().clear();
    }

    public void onEnemyDamaged(Enemy enemy, Weapon equippedWeapon, int damageCaused) {
        updateEnemyInfo(enemy);
        updateFightLog(enemy + " damaged by " + damageCaused + "HP");

        String fileName = (enemy.getHealthPoints() > 0) ?
                equippedWeapon.getHitSoundFileName() : enemy.getDeathSoundFileName();
        try {
            playSoundEffect(fileName);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            System.out.println("En error occurred with a sound effect " + e);
        }
    }

    public void onPlayerDamaged(Player player, int damageCaused) {
        updatePlayerInfo(player);
        updateFightLog("Player damaged by " + damageCaused + "HP");
    }

    public void updateGameWindow(ArrayList<Wall> walls, Weapon weapon, Enemy enemy) {
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
