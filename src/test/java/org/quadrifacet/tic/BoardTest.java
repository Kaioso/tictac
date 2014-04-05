package org.quadrifacet.tic;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class BoardTest extends TestCase {

    private Board game;
    private VictoryCondition victory;

    public void testInitializeGame() throws Exception {
        Board initGame = new Board();
        assertEquals("- - - - - - - - -", boardAsString(initGame.getBoard()));
        assertTrue(initGame.isCrossTurn());
    }

    public void testGenerateBoardFromString_ErrorsOnInvalidLength() throws Exception {
        try {
            Board initGame = Board.fromString("- - - - - -", "X");
            fail();
        } catch (Board.InvalidBoard e) {
            // Good... Good...
        }
    }

    public void testGenerateBoardFromString_ErrorsOnNullInput() throws Exception {
        try {
            Board initGame = Board.fromString(null, null);
            fail();
        } catch (Board.InvalidBoard e) {
            // Good... Good...
        }
    }

    public String boardAsString(List<BoardPosition> board) {
        String display = "";
        for (BoardPosition item : board)
            if (item.isCrossPosition())
                display += "X ";
            else if (item.isNaughtPosition())
                display += "O ";
            else
                display += "- ";
        return display.trim();
    }

    public void testGeneratBoardFromString() throws Exception {
        String board = "- - - - - - - - X";
        Board initGame = Board.fromString(board, "O");
        assertEquals(board, boardAsString(initGame.getBoard()));
        assertTrue(initGame.isNaughtTurn());
    }

    public void setUp() throws Exception {
        super.setUp();
        game = new Board();
        victory = new VictoryCondition(game);
    }

    public void testMove_ErrorsOnInputTooLow() throws Exception {
        try {
            game.play(-1);
            fail();
        } catch (Board.InvalidPosition e) {
            // Good... Good...
        }
    }

    public void testMove_ErrorsOnInputTooHigh() throws Exception {
        try {
            game.play(10);
            fail();
        } catch (Board.InvalidPosition e) {
            // Good... Good...
        }
    }

    public void testMove_ErrorsOnNonEmptySpace() throws Exception {
        try {
            game.play(9);
            game.play(9);
            fail();
        } catch (Board.AlreadyChosen e) {
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
        VictoryCondition rowOne = new VictoryCondition(Board.fromString("X X X - - - - - -", "X"));
        VictoryCondition rowTwo = new VictoryCondition(Board.fromString("- - - X X X - - -", "X"));
        VictoryCondition rowThree = new VictoryCondition(Board.fromString("- - - - - - X X X", "X"));
        assertTrue(rowOne.didCrossWin());
        assertTrue(rowTwo.didCrossWin());
        assertTrue(rowThree.didCrossWin());
    }

    public void testWinsOnColumns() throws Exception {
        VictoryCondition colOne = new VictoryCondition(Board.fromString("O - - O - - O - -", "O"));
        VictoryCondition colTwo = new VictoryCondition(Board.fromString("- O - - O - - O -", "O"));
        VictoryCondition colThree = new VictoryCondition(Board.fromString("- - O - - O - - O", "O"));
        assertTrue(colOne.didNaughtWin());
        assertTrue(colTwo.didNaughtWin());
        assertTrue(colThree.didNaughtWin());
    }

    public void testWinsOnDiagonals() throws Exception {
        VictoryCondition colOne = new VictoryCondition(Board.fromString("O - - - O - - - O", "O"));
        VictoryCondition colTwo = new VictoryCondition(Board.fromString("- - O - O - O - -", "O"));
        assertTrue(colOne.didNaughtWin());
        assertTrue(colTwo.didNaughtWin());
    }

    public void testEndpointScores() throws Exception {
        TicTacAI taiCrossWon = new TicTacAI(Board.fromString("X X X - - - - - -", "X"));
        TicTacAI taiNaughtWon = new TicTacAI(Board.fromString("- - - O O O - - -", "O"));
        TicTacAI taiDraw = new TicTacAI(Board.fromString("X O X O X O O X O", "O"));

        assertEquals(100, taiCrossWon.score());
        assertEquals(-100, taiNaughtWon.score());
        assertEquals(0, taiDraw.score());

    }

    public void testUnfinishedGameScores() throws Exception {
        TicTacAI taiCrossAboutToWin = new TicTacAI(Board.fromString("X X - - - - - - -", "X"));
        TicTacAI taiNaughtAboutToWin = new TicTacAI(Board.fromString("O O - - - - - - -", "O"));

        assertEquals(99, taiCrossAboutToWin.score());
        assertEquals(-99, taiNaughtAboutToWin.score());
    }

    public void testBestMove() throws Exception {
        TicTacAI taiCrossAboutToWin = new TicTacAI(Board.fromString("X X - - - - - - -", "X"));
        TicTacAI taiNaughtAboutToWin = new TicTacAI(Board.fromString("O - - O - - - - -", "O"));
        assertEquals(3, taiCrossAboutToWin.bestMove());
        assertEquals(7, taiNaughtAboutToWin.bestMove());
    }
}
