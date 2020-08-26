package com.example.minesweeper;

import java.util.Scanner;

public class GameRunner {
    public static int init() {
        System.out.println("Please select difficulty: ");
        System.out.println("Select 1 for BEGINNER (9 * 9 and 10 mines)");
        System.out.println("Select 2 for INTERMEDIATE (16 * 16 and 40 mines)");
        System.out.println("Select 3 for BEGINNER (24 * 24 and 99 mines)");
        Scanner scanner = new Scanner(System.in);

        int diff;
        while (true) {
            try {
                diff = Integer.parseInt(scanner.nextLine());
                if (diff > 3 || diff < 1) {
                    System.out.println("Please select from 1 to 3!");
                    diff = Integer.parseInt(scanner.nextLine());
                }
                return diff;
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid input! Please rerun and type a number");
            }
        }
    }

    public static void RunGame(int diff) {
        int row, column, mineCount, x, y;
        switch (diff) {
            case 2:
                row = 16;
                column = 16;
                mineCount = 40;
                break;
            case 3:
                row = 24;
                column = 24;
                mineCount = 99;
                break;
            // set default to BEGINNER or option 1
            default:
                row = 9;
                column = 9;
                mineCount = 10;
                break;
        }

        Game game = new Game(row, column);
        game.generateMines(row, column, mineCount);
        game.update();

        Scanner scan = new Scanner(System.in);

        boolean firstInput = true;
        //After first move, loops until the game ends.
        while (true) {
            System.out.println("Enter your move(row and column, split by ', '):");
            String[] input = scan.nextLine().split(", ");
            x = Integer.parseInt(input[0]) + 1;
            y = Integer.parseInt(input[1]) + 1;

            // Check if the first input is on a mine
            if (firstInput && game.checkIfTileIsMine(x, y)) {
                firstInput = false;
                game.generateMines(row, column, mineCount);
            }

            //If the player wins
            if (game.getDone() && game.getWin()) {
                System.out.println("You win!");
                game.onEnd();
                break;
            }
            //If the player loses
            else if (game.getDone()) {
                game.onEnd();
                break;
            }
            //While the player hasn't lost or won
            else if (!game.getDone()) {
                game.turn(x, y);
                game.reveal(x, y);
                game.update();
                game.isVictory();
            }
        }
    }
}
