package com.example.snake_game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Apple {
    private int x;
    private int y;
    private static final int SIZE = 50;
    public Apple(int boardWidth, int boardHeight) {
        spawn(boardWidth, boardHeight);
    }
    public void spawn(int boardWidth, int boardHeight) {
        x = (int) (Math.random() * (boardWidth/SIZE)) * SIZE;
        y = (int) (Math.random() * (boardHeight/SIZE)) * SIZE;
    }
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillOval(x, y, SIZE, SIZE);
    }
}
