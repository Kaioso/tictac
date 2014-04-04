package org.quadrifacet.tic;

import junit.framework.TestCase;
import org.quadrifacet.tic.mock.*;

import java.util.Arrays;

public class GameTest extends TestCase {
    public void testAnnouncesTitleWhenGameStarts() throws Exception {
        FirstTurnGamePresenter p = new FirstTurnGamePresenter();
        runGame(p);
        assertTrue(p.announceTitleCalled);
    }

    public void testAskPlayerWhatTokenHeWishesToPlay() throws Exception {
        FirstTurnGamePresenter p = new FirstTurnGamePresenter();
        runGame(p);
        assertEquals(Arrays.asList("X", "O"), p.playerOptions);
    }

    public void testCrossesAndNaughtsOnlyAcceptableChoice() throws Exception {
        InvalidTokenPresenter p = new InvalidTokenPresenter();
        runGame(p);
        assertEquals("O", p.eventualToken);
        assertTrue(p.tokenChoiceCalledOnce);
    }

    public void testRequestsDisplayOfCurrentTurnAndBoard() throws Exception {
        FirstTurnGamePresenter p = new FirstTurnGamePresenter();
        runGame(p);
        assertEquals("X", p.firstTurnDisplay);
        assertEquals("- - - - - - - - -", p.firstBoardDisplay);
    }

    public void testExitsGameIfAsked() throws Exception {
        FirstTurnGamePresenter p = new FirstTurnGamePresenter();
        runGame(p);
        assertEquals(1, p.turnNumber);
    }

    public void testGivesListOfPossibleChoicesAndAcceptsChoice() throws Exception {
        SecondTurnGamePresenter p = new SecondTurnGamePresenter();
        runGame(p);
        assertEquals("X", p.secondTurnDisplay);
        assertEquals(4, p.selectedBoardPosition);
    }

    public void testInvalidInputAsksAgain() throws Exception {
        InvalidInputGamePresenter p = new InvalidInputGamePresenter();
        runGame(p);
        assertTrue(p.invalidCalled);
        assertEquals("X", p.turnAfterRedo);
        assertEquals(3, p.selectedItemAfterRedo);
    }

    public void testAnnouncesTerminated() throws Exception {
        SecondTurnGamePresenter p = new SecondTurnGamePresenter();
        runGame(p);
        assertTrue(p.terminatedCalled);
    }

    public void testAnnounceDraw() throws Exception {
        SecondTurnGamePresenter p = new SecondTurnGamePresenter();
        RiggedGame g = new RiggedGame(p, "O X O O X O X O X");
        g.run();
        assertTrue(p.drawCalled);
    }

    public void testAnnounceGameWinner() throws Exception {
        SecondTurnGamePresenter p = new SecondTurnGamePresenter();
        RiggedGame g = new RiggedGame(p, "X X X O X O X O X");
        g.run();
        assertEquals("X", p.gameWinner);
    }

    public void testCyclesThroughPlayerTurns() throws Exception {
        PlayerCycleGamePresenter p = new PlayerCycleGamePresenter();
        runGame(p);
        assertFalse(p.didNotCycle);
    }

    private void runGame(MockGamePresenter presenter) {
        new Game(presenter, presenter).run();
    }

    private class RiggedGame extends Game {
        private final GameBoard fixedBoard;
        private RiggedGame(MockGamePresenter presenter, String boardString) {
            super(presenter, presenter);
            this.fixedBoard = GameBoard.fromString(boardString, "X");
        }

        @Override
        protected void initializeGameActors() {
            this.board = this.fixedBoard;
            this.victory = new VictoryCondition(this.board);
            this.ai = new TicTacAI(this.board);
        }
    }
}
