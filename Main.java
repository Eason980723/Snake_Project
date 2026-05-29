package com.example.demo;
import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import java.io.IOException;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main extends Application {

    Deque<Point> snake = new ArrayDeque<>();
    int dx = 1, dy = 0;

    @Override
    public void start(Stage stage) throws IOException {
        int boardWith ;
        int boardHeight;
        Canvas canvas;
        StackPane root;
        Scene scene;
        Scanner input = new Scanner(System.in);
        System.out.println("Select the gamemode (Easy/ Medium/ Hard)");
        String gamemode = input.nextLine();
        if (gamemode.equalsIgnoreCase("Easy")) {
            boardWith = 200;
            boardHeight = 200;
            canvas = new Canvas(200, 200);
            root = new StackPane(canvas);
            scene = new Scene(root, 200, 200);

        }
        else if (gamemode.equalsIgnoreCase("Medium")) {
            boardWith = 400;
            boardHeight = 400;
            canvas = new Canvas(400, 400);
            root = new StackPane(canvas);
            scene = new Scene(root, 400, 400);
        }
        else{
            boardWith = 600;
            boardHeight = 600;
            canvas = new Canvas(600, 600);
            root = new StackPane(canvas);
            scene = new Scene(root, 600, 600);
        }

        //build snake
        snake.addFirst(new Point(7,10));
        snake.addFirst(new Point(8,10));
        snake.addFirst(new Point(9,10));

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()){
                case UP -> { if (dy==0){dx=0;dy=-1;}}
                case DOWN -> { if (dy==0){dx=0;dy=1;}}
                case LEFT -> { if (dy==0){dx=-1;dy=0;}}
                case RIGHT -> { if (dy==0){dx=1;dy=0;}}
            }
        });
        GraphicsContext gc = canvas.getGraphicsContext2D();
        new AnimationTimer() {
            long last = 0;
            public void handle(long now) {
                if (now - last < 150_000_000L) return;
                last = now;

                // Move: add new head, drop tail
                Point head = snake.peekFirst();
                Point next = new Point(head.x + dx, head.y + dy);
                snake.addFirst(next);
                snake.removeLast();

                // Draw
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, boardWith, boardHeight);

                gc.setFill(Color.LIMEGREEN);
                for (Point p : snake)
                    gc.fillRoundRect(p.x * 50 + 1, p.y * 50 + 1,
                            50 - 2, 50 - 2, 4, 4);
            }
        }.start();

        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.show();


        int tileSize = 50; // each block is 20x20 pixels

// Black background
        for (int x = 0; x < canvas.getWidth()/ tileSize; x++) {
            for (int y = 0; y < canvas.getHeight() / tileSize; y++) {
                if ((x + y) % 2 == 0) {
                    gc.setFill(Color.web("#AAD751"));
                    ;
                } else {
                    gc.setFill(Color.web("#A2D149"));
                }
                gc.fillRect(x * tileSize, y * tileSize,  tileSize, tileSize);
            }
        }

// White border around the whole board
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Apple apple = new Apple (boardWith, boardHeight);
        apple.draw(gc);

    }
}
