package org.quadrifacet.tic;

import junit.framework.TestCase;
import org.quadrifacet.tic.mock.*;

public class GameTest extends TestCase {
    public void testAnnouncesTitleWhenGameStarts() throws Exception {
        MockStatusAnnouncer status = new MockStatusAnnouncer();
        Presentation pr = new Presentation(status, new RandomGuesserReader(), new MockWinAnnouncer());
        runGame(pr);
        assertTrue(status.gameTitleCalled);
    }

    public void testAskPlayerWhatTokenHeWishesToPlay() throws Exception {
        RandomGuesserReader reader = new RandomGuesserReader().chooseNaught();
        Presentation pr = new Presentation(new MockStatusAnnouncer(), reader, new MockWinAnnouncer());
        RiggedGame g = new RiggedGame(pr, "- - - - - - - - -");
        g.run();
        assertFalse(g.playerIsCross());
    }

    public void testRequestsDisplayOfCurrentTurnAndBoard() throws Exception {
        MockStatusAnnouncer status = new MockStatusAnnouncer();
        Presentation pr = new Presentation(status, new RandomGuesserReader(), new MockWinAnnouncer());
        runGame(pr);
        assertTrue(status.displayGameStateCalled);
        assertEquals("- - - - - - - - -", status.firstBoardDisplayed);
    }

    public void testExitsGameIfAsked() throws Exception {
        RandomGuesserReader reader = new RandomGuesserReader().exitAfterTurn(1);
        Presentation pr = new Presentation(new MockStatusAnnouncer(), reader, new MockWinAnnouncer());
        runGame(pr);
        assertEquals(1, reader.playerTurnsCounted);
    }

    public void testGivesListOfPossibleChoicesAndAcceptsChoice() throws Exception {
        MockWinAnnouncer winAnnouncer = new MockWinAnnouncer();
        Presentation pr = new Presentation(new MockStatusAnnouncer(), new RandomGuesserReader(), winAnnouncer);
        runGame(pr);
        assertTrue(winAnnouncer.crossWon || winAnnouncer.naughtWon || winAnnouncer.gameDrawed);
    }

    public void testInvalidInputAsksAgain() throws Exception {
        RandomGuesserReader reader = new RandomGuesserReader().guessGarbageAndThrowException();
        Presentation pr = new Presentation(new MockStatusAnnouncer(), reader, new MockWinAnnouncer());

        try {
            runGame(pr);
            fail();
        } catch (RandomGuesserReader.AttemptedInvalidMove e) {}
    }

    public void testAnnouncesTerminated() throws Exception {
        MockStatusAnnouncer status = new MockStatusAnnouncer();
        Presentation pr = new Presentation(status, new RandomGuesserReader().exitAfterTurn(1), new MockWinAnnouncer());
        runGame(pr);
        assertTrue(status.gameTerminatedCalled);
    }

    public void testAnnounceDraw() throws Exception {
        MockWinAnnouncer a = new MockWinAnnouncer();
        Presentation pr = new Presentation(new MockStatusAnnouncer(), new RandomGuesserReader(), a);
        RiggedGame g = new RiggedGame(pr, "O X O O X O X O X");
        g.run();

        assertTrue(a.gameDrawed);
    }

    public void testAnnounceGameWinner() throws Exception {
        MockWinAnnouncer a = new MockWinAnnouncer();
        Presentation pr = new Presentation(new MockStatusAnnouncer(), new RandomGuesserReader(), a);
        RiggedGame g = new RiggedGame(pr, "X X X O X O X O X");
        g.run();

        assertTrue(a.crossWon);
    }

    private void runGame(Presentation presentation) {
        new Game(presentation).run();
    }

    private class RiggedGame extends Game {
        private final Board fixedBoard;

        private RiggedGame(Presentation pr, String boardString) {
            super(pr);
            this.fixedBoard = Board.fromString(boardString, "X");
        }

        @Override
        protected void initializeGameActors() {
            this.board = this.fixedBoard;
            this.victory = new VictoryCondition();
            this.ai = new TicTacAI(this.board);
        }

        public boolean playerIsCross() {
            return "X".equals(this.playerTurn);
        }
    }
}
