package game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        final int boxSize = 800;
        final int marginTop = 50, marginBottom = 50;
        final int width = boxSize, height = boxSize - (marginTop + marginBottom);

        final GameWindow gameWindow = new GameWindow(boxSize, marginTop, marginBottom);


        Wall[] walls = {
                new Wall(0, 0,0, 1),
                new Wall(1, 0,1, 3),
                new Wall(0, 3,1, 3),

                new Wall(0, 2, -2, 2),
                new Wall(0, 2, 0, 3),
        };
//        for (Wall wall : walls)
//            gameWindow.drawWall(wall);

        gameWindow.drawWalls(walls);

        VBox box = new VBox(
                new Label("hi"),
                gameWindow
        );
        Scene scene = new Scene(
                box,
                850, 700
        );

        primaryStage.setTitle("Dungeon RPG Indev 0.0.1");
        primaryStage.setScene(scene);
        primaryStage.show();


        String file = "src\\main\\resources\\trial_map.txt";
        Path path = Paths.get(file);
        List<String> lines = Files.readAllLines(path);
        for (String line : lines)
            System.out.println(line);
    }
}
