package org.quadrifacet.tic;

import junit.framework.TestCase;

import java.util.Arrays;

public class GameBoardTest extends TestCase {

    private GameBoard game;

    public void testInitializeGame() throws Exception {
        GameBoard initGame = new GameBoard();
        assertEquals("- - - - - - - - -", initGame.getBoardDisplay());
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

    public void testGeneratBoardFromString() throws Exception {
        String board = "- - - - - - - - X";
        GameBoard initGame = GameBoard.fromString(board, "O");
        assertEquals(board, initGame.getBoardDisplay());
        assertEquals("O", initGame.getCurrentTurn());
    }

    public void setUp() throws Exception {
        super.setUp();
        game = new GameBoard();
    }

    public void testMove_ErrorsOnInvalidInput() throws Exception {
        try {
            GameBoard played = game.play(-1);
            played = game.play(10);
            fail();
        } catch (GameBoard.InvalidPosition e) {
            // Good... Good...
        }
    }

    public void testMove() throws Exception {
        GameBoard played = game.play(1).play(3);
        assertEquals("X - O - - - - - -", played.getBoardDisplay());
    }

    public void testOpenPositions() throws Exception {
        GameBoard played = game.play(1).play(5).play(3);
        assertEquals(Arrays.asList(2, 4, 6, 7, 8, 9), played.getOpenPositions());
    }

    public void testKnowsWhenGameIsOver() throws Exception {
        assertFalse(game.isWinFor("X"));
        assertFalse(game.isWinFor("O"));
    }

    public void testWinsOnRows() throws Exception {
        GameBoard rowOne = GameBoard.fromString("X X X - - - - - -", "X");
        GameBoard rowTwo = GameBoard.fromString("- - - X X X - - -", "X");
        GameBoard rowThree = GameBoard.fromString("- - - - - - X X X", "X");
        assertTrue(rowOne.isWinFor("X"));
        assertTrue(rowTwo.isWinFor("X"));
        assertTrue(rowThree.isWinFor("X"));
    }

    public void testWinsOnColumns() throws Exception {
        GameBoard colOne = GameBoard.fromString("O - - O - - O - -", "O");
        GameBoard colTwo = GameBoard.fromString("- O - - O - - O -", "O");
        GameBoard colThree = GameBoard.fromString("- - O - - O - - O", "O");
        assertTrue(colOne.isWinFor("O"));
        assertTrue(colTwo.isWinFor("O"));
        assertTrue(colThree.isWinFor("O"));
    }

    public void testWinsOnDiagonals() throws Exception {
        GameBoard colOne = GameBoard.fromString("O - - - O - - - O", "O");
        GameBoard colTwo = GameBoard.fromString("- - O - O - O - -", "O");
        assertTrue(colOne.isWinFor("O"));
        assertTrue(colTwo.isWinFor("O"));
    }

    public void testEndpointScores() throws Exception {
        assertEquals(100, GameBoard.fromString("X X X - - - - - -", "X").score());
        assertEquals(-100, GameBoard.fromString("- - - O O O - - -", "O").score());
        assertEquals(0, GameBoard.fromString("X O X O X O O X O", "O").score());
    }

    public void testUnfinishedGameScores() throws Exception {
        assertEquals(99, GameBoard.fromString("X X - - - - - - -", "X").score());
        assertEquals(-99, GameBoard.fromString("O O - - - - - - -", "O").score());
    }

    public void testBestMove() throws Exception {
        assertEquals(3, GameBoard.fromString("X X - - - - - - -", "X").bestMove());
        assertEquals(7, GameBoard.fromString("O - - O - - - - -", "O").bestMove());
    }
}
