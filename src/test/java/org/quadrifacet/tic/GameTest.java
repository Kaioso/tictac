package org.quadrifacet.tic;

import junit.framework.TestCase;
import org.quadrifacet.tic.mock.*;

import java.util.Arrays;

public class GameTest extends TestCase {
    public void testAnnouncesTitleWhenGameStarts() throws Exception {
        FirstTurnGameStatusAnnouncer p = new FirstTurnGameStatusAnnouncer();
        runGame(p);
        assertTrue(p.announceTitleCalled);
    }

    public void testAskPlayerWhatTokenHeWishesToPlay() throws Exception {
        FirstTurnGameStatusAnnouncer p = new FirstTurnGameStatusAnnouncer();
        runGame(p);
        assertEquals(Arrays.asList("X", "O"), p.playerOptions);
    }

    public void testCrossesAndNaughtsOnlyAcceptableChoice() throws Exception {
        InvalidTokenStatusAnnouncer p = new InvalidTokenStatusAnnouncer();
        runGame(p);
        assertEquals("O", p.eventualToken);
        assertTrue(p.tokenChoiceCalledOnce);
    }

    public void testRequestsDisplayOfCurrentTurnAndBoard() throws Exception {
        FirstTurnGameStatusAnnouncer p = new FirstTurnGameStatusAnnouncer();
        runGame(p);
        assertEquals("X", p.firstTurnDisplay);
        assertEquals("- - - - - - - - -", p.firstBoardDisplay);
    }

    public void testExitsGameIfAsked() throws Exception {
        FirstTurnGameStatusAnnouncer p = new FirstTurnGameStatusAnnouncer();
        runGame(p);
        assertEquals(1, p.turnNumber);
    }

    public void testGivesListOfPossibleChoicesAndAcceptsChoice() throws Exception {
        SecondTurnGameStatusAnnouncer p = new SecondTurnGameStatusAnnouncer();
        runGame(p);
        assertEquals("X", p.secondTurnDisplay);
        assertEquals(4, p.selectedBoardPosition);
    }

    public void testInvalidInputAsksAgain() throws Exception {
        InvalidInputGameStatusAnnouncer p = new InvalidInputGameStatusAnnouncer();
        runGame(p);
        assertTrue(p.invalidCalled);
        assertEquals("X", p.turnAfterRedo);
        assertEquals(3, p.selectedItemAfterRedo);
    }

    public void testAnnouncesTerminated() throws Exception {
        SecondTurnGameStatusAnnouncer p = new SecondTurnGameStatusAnnouncer();
        runGame(p);
        assertTrue(p.terminatedCalled);
    }

    public void testAnnounceDraw() throws Exception {
        MockWinAnnouncer a = new MockWinAnnouncer();
        GamePresentation pr = new GamePresentation(new NullStatusAnnouncer(), new RandomGuesserReader(), a);
        RiggedGame g = new RiggedGame(pr, "O X O O X O X O X");
        g.run();

        assertTrue(a.gameDrawed);
    }

    public void testAnnounceGameWinner() throws Exception {
        MockWinAnnouncer a = new MockWinAnnouncer();
        GamePresentation pr = new GamePresentation(new NullStatusAnnouncer(), new RandomGuesserReader(), a);
        RiggedGame g = new RiggedGame(pr, "X X X O X O X O X");
        g.run();

        assertTrue(a.crossWon);
    }

    public void testCyclesThroughPlayerTurns() throws Exception {
        PlayerCycleGameStatusAnnouncer p = new PlayerCycleGameStatusAnnouncer();
        runGame(p);
        assertFalse(p.didNotCycle);
    }

    private void runGame(MockGameStatusAnnouncer presenter) {
        new Game(presenter, presenter).run();
    }

    private class RiggedGame extends Game {
        private final GameBoard fixedBoard;

        private RiggedGame(GamePresentation pr, String boardString) {
            super(pr);
            this.fixedBoard = GameBoard.fromString(boardString, "X");
        }

        @Override
        protected void initializeGameActors() {
            this.board = this.fixedBoard;
            this.victory = new VictoryCondition(this.board, this.winAnnouncer);
            this.ai = new TicTacAI(this.board);
        }
    }
}
