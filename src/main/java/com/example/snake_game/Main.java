package com.example.snake_game;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas;
        StackPane root;
        Scene scene;
        Scanner input = new Scanner(System.in);
        System.out.println("Select the gamemode (Easy/ Medium/ Hard)");
        String gamemode = input.nextLine();
        if (gamemode.equalsIgnoreCase("Easy")) {
            canvas = new Canvas(200, 200);
            root = new StackPane(canvas);
            scene = new Scene(root, 200, 200);

        }
        else if (gamemode.equalsIgnoreCase("Medium")) {
            canvas = new Canvas(400, 400);
            root = new StackPane(canvas);
            scene = new Scene(root, 400, 400);
        }
        else{
            canvas = new Canvas(600, 600);
            root = new StackPane(canvas);
            scene = new Scene(root, 600, 600);
        }

        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.show();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        int tileSize = 50; // each block is 20x20 pixels

// Black background
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

// Draw the grid lines
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.5);

// Vertical lines
        for (int x = 0; x <= canvas.getWidth(); x += tileSize) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }

// Horizontal lines
        for (int y = 0; y <= canvas.getHeight(); y += tileSize) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }

// White border around the whole board
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }
}
