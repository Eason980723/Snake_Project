package com.example.snake_game;
import java.util.Scanner;
import javafx.application.Application;
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
import java.util.List;
import java.util.ArrayList;

public class Main extends Application {

    Deque<Point> snake = new ArrayDeque<>();
    int dx = 0, dy = 0;

    @Override
    public void start(Stage stage) throws IOException {
        int boardWith;
        int boardHeight;
        Canvas canvas;
        StackPane root;
        Scene scene;
        Scanner input = new Scanner(System.in);
        System.out.println("Select the gamemode (Easy/ Medium/ Hard)");
        String gamemode = input.nextLine();
        if (gamemode.equalsIgnoreCase("Easy")) {
            boardWith = 400;
            boardHeight = 400;
            canvas = new Canvas(400, 400);
            root = new StackPane(canvas);
            scene = new Scene(root, 400, 400);
        } else if (gamemode.equalsIgnoreCase("Medium")) {
            boardWith = 550;
            boardHeight = 550;
            canvas = new Canvas(550, 550);
            root = new StackPane(canvas);
            scene = new Scene(root, 550, 550);
        } else {
            boardWith = 700;
            boardHeight = 700;
            canvas = new Canvas(700, 700);
            root = new StackPane(canvas);
            scene = new Scene(root, 700, 700);
        }

        final int tileSize = 50;
        final int bw = boardWith;
        final int bh = boardHeight;

        int centerX = (boardWith / tileSize) / 2;
        int centerY = (boardHeight / tileSize) / 2;
        snake.addFirst(new Point(centerX - 1, centerY));
        snake.addFirst(new Point(centerX,     centerY));
        snake.addFirst(new Point(centerX + 1, centerY));

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case UP    -> { if (dy != 1)  { dx = 0;  dy = -1; } }
                case DOWN  -> { if (dy != -1) { dx = 0;  dy =  1; } }
                case LEFT  -> { if (dx != 1)  { dx = -1; dy =  0; } }
                case RIGHT -> { if (dx != -1) { dx =  1; dy =  0; } }
            }
        });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        Apple apple = new Apple(boardWith, boardHeight);

        new AnimationTimer() {
            boolean gameOver = false;
            int moveCount = 0;
            int score = 0;
            long last = 0;

            public void handle(long now) {
                if (gameOver) return;
                if (now - last < 150_000_000L) return;
                last = now;

                if (dx != 0 || dy != 0) {
                    moveCount++;
                    Point head = snake.peekFirst();
                    Point next = new Point(head.x + dx, head.y + dy);

                    if (next.x < 0 || next.y < 0 || next.x >= bw / tileSize || next.y >= bh / tileSize) {
                        gameOver = true;
                        gc.setFill(Color.BLACK);
                        gc.fillRect(0, 0, bw, bh);
                        gc.setFill(Color.WHITE);
                        gc.setFont(javafx.scene.text.Font.font(40));
                        gc.fillText("Game Over!", bw / 2 - 90, bh / 2 - 30);
                        gc.fillText("Score: " + score, bw / 2 - 70, bh / 2 + 30);
                        return;
                    }

                    if (moveCount > 3) {
                        List<Point> body = new ArrayList<>(snake);
                        body.remove(body.size() - 1);
                        for (Point p : body) {
                            if (next.x == p.x && next.y == p.y) {
                                gameOver = true;
                                gc.setFill(Color.BLACK);
                                gc.fillRect(0, 0, bw, bh);
                                gc.setFill(Color.WHITE);
                                gc.setFont(javafx.scene.text.Font.font(40));
                                gc.fillText("Game Over!", bw / 2 - 90, bh / 2 - 30);
                                gc.fillText("Score: " + score, bw / 2 - 70, bh / 2 + 30);
                                return;
                            }
                        }
                    }

                    snake.addFirst(next);
                    if (next.x == apple.getX() && next.y == apple.getY()) {
                        apple.spawn(bw, bh);
                        score++;
                    } else {
                        snake.removeLast();
                    }
                }

                for (int x = 0; x < bw / tileSize; x++) {
                    for (int y = 0; y < bh / tileSize; y++) {
                        if ((x + y) % 2 == 0) gc.setFill(Color.web("#AAD751"));
                        else gc.setFill(Color.web("#A2D149"));
                        gc.fillRect(x * tileSize, y * tileSize, tileSize, tileSize);
                    }
                }

                apple.draw(gc);

                gc.setFill(Color.LIMEGREEN);
                for (Point p : snake)
                    gc.fillRoundRect(p.x * tileSize + 1, p.y * tileSize + 1,
                            tileSize - 2, tileSize - 2, 4, 4);

                gc.setStroke(Color.WHITE);
                gc.setLineWidth(2);
                gc.strokeRect(0, 0, bw, bh);

                gc.setFill(Color.WHITE);
                gc.setFont(javafx.scene.text.Font.font(20));
                gc.fillText("Score: " + score, 10, 25);
            }
        }.start();

        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.show();
    }
}