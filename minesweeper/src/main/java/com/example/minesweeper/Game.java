package com.example.minesweeper;


import java.util.Random;

import static java.lang.String.format;

public class Game {
    private int[][] field;
    private String[][] display;
    private boolean[][] revealed;
    private Boolean isDone = false;
    private Boolean isWin = false;

    private final String unknown = " - ";
    private final String empty = "   ";

    public Game(int row, int column) {
        // adding +1 for numbers row and column
        field = new int[row + 1][column + 1];
        display = new String[row + 1][column + 1];
        revealed = new boolean[row + 1][column + 1];

        // Filling the grid with empty slot in the corner and numbers row and column for easy input. All others are unknown
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[0].length; y++) {
                if (x == 0 && y == 0) {
                    display[x][y] = empty;
                    revealed[x][y] = true;
                    field[x][y] = 2;
                } else if (x == 0) {
                    display[x][y] = format(" %d ", (y - 1));
                    revealed[x][y] = true;
                    field[x][y] = 2;
                } else if (y == 0) {
                    display[x][y] = format(" %d ", (x - 1));
                    revealed[x][y] = true;
                    field[x][y] = 2;
                } else {
                    display[x][y] = unknown;
                    field[x][y] = 0;
                }
            }
        }
    }

    //A method to count the mines around a target
    public int detectMines(int row, int col) {
        if (outOfBounds(row, col)) return 0;
        int i = 0;
        for (int offsetX = -1; offsetX <= 1; offsetX++) {
            for (int offsetY = -1; offsetY <= 1; offsetY++) {
                if (outOfBounds(offsetX + row, offsetY + col)) continue;
                if (field[offsetX + row][offsetY + col] == 1)
                    i += 1;
            }
        }
        display[row][col] = format(" %d ", i);
        return i;
    }

    // A method to reveal other cells if target is empty and with zero mines around it
    void reveal(int x, int y) {
        if (outOfBounds(x, y)) return;
        if (revealed[x][y]) return;
        revealed[x][y] = true;
        if (detectMines(x, y) != 0) return;
        reveal(x - 1, y - 1);
        reveal(x - 1, y + 1);
        reveal(x + 1, y - 1);
        reveal(x + 1, y + 1);
        reveal(x - 1, y);
        reveal(x + 1, y);
        reveal(x, y - 1);
        reveal(x, y + 1);
    }

    //Takes user's selected coordinates and adjusts the board.
    public void turn(int row, int col) {
        if (field[row][col] == 0) {
            isDone = false;
            display[row][col] = empty;
        } else if (field[row][col] == 1) {
            isDone = true;
            isWin = false;
            onEnd();
            System.out.println("You lost!");
        }
    }

    //Displays the field
    public static void printGame(String[][] str) {
        System.out.println("Current status of board:");
        for (String[] strings : str) {
            for (int y = 0; y < str[0].length; y++) {
                System.out.print(strings[y]);
            }
            System.out.println();
        }
    }

    //Updates the console after every match
    public void update() {
        printGame(display);
    }

    //Places n mines at random on the field.
    public void generateMines(int row, int col, int mineCount) {
        int i = 0;
        Random random = new Random();
        //We don't want mines to overlap, so we do while loop
        while (i < mineCount) {
            int x = random.nextInt(row);
            int y = random.nextInt(col);
            if (field[x][y] == 1) continue;
            if (field[x][y] == 2) continue;
            field[x][y] = 1;
            revealed[x][y] = true;
            i++;
        }
    }

    // Method to check if the game is won
    public void isVictory() {
        int tile = 0;
        for (String[] strings : display) {
            for (int j = 0; j < display[0].length; j++) {
                if (strings[j].equals(unknown))
                    tile++;
            }
        }
        if (tile != 0)
            isWin = false;
        else {
            isWin = true;
            isDone = true;
        }
    }

    public boolean checkIfTileIsMine(int row, int col) {
        return field[row][col] == 1;
    }

    public Boolean getDone() {
        return isDone;
    }

    public Boolean getWin() {
        return isWin;
    }

    public void onEnd() {
        for (int row = 0; row < field.length; row++) {
            for (int col = 0; col < field[0].length; col++) {
                if (field[row][col] == 1) {
                    String mine = " * ";
                    display[row][col] = mine;
                }
            }
        }
    }

    private boolean outOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= field.length || y >= field.length;
    }
}
