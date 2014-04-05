package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.BoardPosition;
import org.quadrifacet.tic.State;
import org.quadrifacet.tic.StatusAnnouncer;

import java.util.List;

public class MockStatusAnnouncer implements StatusAnnouncer {
    public boolean gameTitleCalled = false;
    public boolean displayGameStateCalled = false;
    public boolean gameTerminatedCalled = false;

    public String firstBoardDisplayed = "";


    @Override
    public void announceGameTitle() {
        gameTitleCalled = true;
    }

    @Override
    public void displayGameState(State state) {
        displayGameStateCalled = true;
        if (firstBoardDisplayed.isEmpty())
            firstBoardDisplayed = boardAsString(state.getBoard());
    }

    private String boardAsString(List<BoardPosition> board) {
        String display = "";
        for (BoardPosition item : board) {
            if (item.isCrossPosition())
                display += "X ";
            else if (item.isNaughtPosition())
                display += "O ";
            else
                display += "- ";
        }
        return display.trim();
    }

    @Override
    public void announceGameTerminated() {
        gameTerminatedCalled = true;
    }
}
