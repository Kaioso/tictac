package org.quadrifacet.tic;

import junit.framework.TestCase;

import java.util.Arrays;

public class GameBoardTest extends TestCase {

    private GameBoard game;
    private VictoryCondition victory;
    private TicTacAI ai;

    public void testInitializeGame() throws Exception {
        GameBoard initGame = new GameBoard();
        assertEquals("- - - - - - - - -", boardAsString(initGame.getBoard()));
        assertEquals("X", initGame.getCurrentTurn());
    }

    public void testGenerateBoardFromString_ErrorsOnInvalidLength() throws Exception {
        try {
            GameBoard initGame = GameBoard.fromString("- - - - - -", "X");
            fail();
        } catch (GameBoard.InvalidBoard e) {
            // Good... Good...
        }
    }

    public void testGenerateBoardFromString_ErrorsOnNullInput() throws Exception {
        try {
            GameBoard initGame = GameBoard.fromString(null, null);
            fail();
        } catch (GameBoard.InvalidBoard e) {
            // Good... Good...
        }
    }

    public String boardAsString(String[] board) {
        String display = "";
        for (String item : board)
            display += item + " ";
        return display.trim();
    }

    public void testGeneratBoardFromString() throws Exception {
        String board = "- - - - - - - - X";
        GameBoard initGame = GameBoard.fromString(board, "O");
        assertEquals(board, boardAsString(initGame.getBoard()));
        assertEquals("O", initGame.getCurrentTurn());
    }

    public void setUp() throws Exception {
        super.setUp();
        game = new GameBoard();
        victory = new VictoryCondition(game);
        ai = new TicTacAI(game);
    }

    public void testMove_ErrorsOnInputTooLow() throws Exception {
        try {
            game.play(-1);
            fail();
        } catch (GameBoard.InvalidPosition e) {
            // Good... Good...
        }
    }

    public void testMove_ErrorsOnInputTooHigh() throws Exception {
        try {
            game.play(10);
            fail();
        } catch (GameBoard.InvalidPosition e) {
            // Good... Good...
        }
    }

    public void testMove_ErrorsOnNonEmptySpace() throws Exception {
        try {
            game.play(9);
            game.play(9);
            fail();
        } catch (GameBoard.AlreadyChosen e) {
            // Good... Good...
        }
    }

    public void testMove() throws Exception {
        game.play(1);
        game.play(3);
        assertEquals("X - O - - - - - -", boardAsString(game.getBoard()));
    }

    public void testOpenPositions() throws Exception {
        game.play(1);
        game.play(5);
        game.play(3);
        assertEquals(Arrays.asList(2, 4, 6, 7, 8, 9), game.getOpenPositions());
    }

    public void testKnowsWhenGameIsOver() throws Exception {
        assertFalse(victory.didCrossWin());
        assertFalse(victory.didNaughtWin());
    }

    public void testWinsOnRows() throws Exception {
        VictoryCondition rowOne = new VictoryCondition(GameBoard.fromString("X X X - - - - - -", "X"));
        VictoryCondition rowTwo = new VictoryCondition(GameBoard.fromString("- - - X X X - - -", "X"));
        VictoryCondition rowThree = new VictoryCondition(GameBoard.fromString("- - - - - - X X X", "X"));
        assertTrue(rowOne.didCrossWin());
        assertTrue(rowTwo.didCrossWin());
        assertTrue(rowThree.didCrossWin());
    }

    public void testWinsOnColumns() throws Exception {
        VictoryCondition colOne = new VictoryCondition(GameBoard.fromString("O - - O - - O - -", "O"));
        VictoryCondition colTwo = new VictoryCondition(GameBoard.fromString("- O - - O - - O -", "O"));
        VictoryCondition colThree = new VictoryCondition(GameBoard.fromString("- - O - - O - - O", "O"));
        assertTrue(colOne.didNaughtWin());
        assertTrue(colTwo.didNaughtWin());
        assertTrue(colThree.didNaughtWin());
    }

    public void testWinsOnDiagonals() throws Exception {
        VictoryCondition colOne = new VictoryCondition(GameBoard.fromString("O - - - O - - - O", "O"));
        VictoryCondition colTwo = new VictoryCondition(GameBoard.fromString("- - O - O - O - -", "O"));
        assertTrue(colOne.didNaughtWin());
        assertTrue(colTwo.didNaughtWin());
    }

    public void testEndpointScores() throws Exception {
        TicTacAI taiCrossWon = new TicTacAI(GameBoard.fromString("X X X - - - - - -", "X"));
        TicTacAI taiNaughtWon = new TicTacAI(GameBoard.fromString("- - - O O O - - -", "O"));
        TicTacAI taiDraw = new TicTacAI(GameBoard.fromString("X O X O X O O X O", "O"));

        assertEquals(100, taiCrossWon.score());
        assertEquals(-100, taiNaughtWon.score());
        assertEquals(0, taiDraw.score());

    }

    public void testUnfinishedGameScores() throws Exception {
        TicTacAI taiCrossAboutToWin = new TicTacAI(GameBoard.fromString("X X - - - - - - -", "X"));
        TicTacAI taiNaughtAboutToWin = new TicTacAI(GameBoard.fromString("O O - - - - - - -", "O"));

        assertEquals(99, taiCrossAboutToWin.score());
        assertEquals(-99, taiNaughtAboutToWin.score());
    }

    public void testBestMove() throws Exception {
        TicTacAI taiCrossAboutToWin = new TicTacAI(GameBoard.fromString("X X - - - - - - -", "X"));
        TicTacAI taiNaughtAboutToWin = new TicTacAI(GameBoard.fromString("O - - O - - - - -", "O"));
        assertEquals(3, taiCrossAboutToWin.bestMove());
        assertEquals(7, taiNaughtAboutToWin.bestMove());
    }
}
