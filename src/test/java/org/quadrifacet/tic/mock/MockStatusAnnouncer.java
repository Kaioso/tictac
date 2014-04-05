package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.GameStatusAnnouncer;

import java.util.List;

public class MockStatusAnnouncer implements GameStatusAnnouncer {
    public boolean gameTitleCalled = false;
    public boolean displayGameStateCalled = false;
    public boolean gameTerminatedCalled = false;

    public String firstBoardDisplayed = "";


    @Override
    public void announceGameTitle() {
        gameTitleCalled = true;
    }

    @Override
    public void displayGameState(String currentTurn, String[] board, List<String> openPositions) {
        displayGameStateCalled = true;
        if (firstBoardDisplayed.isEmpty())
            firstBoardDisplayed = boardAsString(board);
    }

    private String boardAsString(String[] board) {
        String display = "";
        for (String item : board) {
            display += item + " ";
        }
        return display.trim();
    }

    @Override
    public void announceGameTerminated() {
        gameTerminatedCalled = true;
    }
}
