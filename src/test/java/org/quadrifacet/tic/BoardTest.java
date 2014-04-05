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
        victory = new VictoryCondition();
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
            game.play(8);
            game.play(8);
            fail();
        } catch (Board.AlreadyChosen e) {
            // Good... Good...
        }
    }

    public void testMove() throws Exception {
        game.play(0);
        game.play(2);
        assertEquals("X - O - - - - - -", boardAsString(game.getBoard()));
    }

    public void testOpenPositions() throws Exception {
        game.play(0);
        game.play(4);
        game.play(2);
        assertEquals(Arrays.asList(1, 3, 5, 6, 7, 8), game.getOpenPositions());
    }

    public void testKnowsWhenGameIsOver() throws Exception {
        assertFalse(victory.didCrossWin(Board.fromString("- - - - - - - - -", "X")));
        assertFalse(victory.didNaughtWin(Board.fromString("- - - - - - - - -", "X")));
    }

    public void testWinsOnRows() throws Exception {
        VictoryCondition victory = new VictoryCondition();
        assertTrue(victory.didCrossWin(Board.fromString("X X X - - - - - -", "X")));
        assertTrue(victory.didCrossWin(Board.fromString("- - - X X X - - -", "X")));
        assertTrue(victory.didCrossWin(Board.fromString("- - - - - - X X X", "X")));
    }

    public void testWinsOnColumns() throws Exception {
        VictoryCondition victory = new VictoryCondition();
        assertTrue(victory.didNaughtWin(Board.fromString("O - - O - - O - -", "O")));
        assertTrue(victory.didNaughtWin(Board.fromString("- O - - O - - O -", "O")));
        assertTrue(victory.didNaughtWin(Board.fromString("- - O - - O - - O", "O")));
    }

    public void testWinsOnDiagonals() throws Exception {
        VictoryCondition victory = new VictoryCondition();
        assertTrue(victory.didNaughtWin(Board.fromString("O - - - O - - - O", "O")));
        assertTrue(victory.didNaughtWin(Board.fromString("- - O - O - O - -", "O")));
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
        assertEquals(2, taiCrossAboutToWin.bestMove());
        assertEquals(6, taiNaughtAboutToWin.bestMove());
    }
}
