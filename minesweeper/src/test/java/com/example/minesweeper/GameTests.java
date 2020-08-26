package com.example.minesweeper;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTests {
    int row;
    int col;
    int mineCount;
    Game game;
    GameRunner gameRunner;
    int[][] testArr;

    @Before
    public void before() {
        row = 2;
        col = 2;
        mineCount = 2;
        game = new Game(row, col);
        gameRunner = new GameRunner();
        testArr = new int[row][col];
    }

    @Test
    public void DetectMinesShouldReturnZeroWhenThereIsNoMines() {
        //Arrange
        //Act
        int detect = game.detectMines(row, col);

        //Assert
        Assert.assertEquals(detect, 0);
    }

}
