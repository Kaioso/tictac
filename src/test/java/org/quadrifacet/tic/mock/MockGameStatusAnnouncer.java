package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.GameInputReader;
import org.quadrifacet.tic.GameStatusAnnouncer;

import java.util.List;

public abstract class MockGameStatusAnnouncer implements GameStatusAnnouncer, GameInputReader {
    public boolean announceTitleCalled = false;
    public List<String> playerOptions = null;
    public int turnNumber = 0;
    public boolean terminatedCalled = false;
    public boolean drawCalled = false;
    public String gameWinner = "";

    @Override
    public void announceGameTitle() {
        announceTitleCalled = true;
    }

    @Override
    public String getNextMove(List<String> openPositions) {
        if (++turnNumber >= exitAfterTurn())
            return "exit";
        else
            return nextMove(openPositions);
    }

    @Override
    public String tryAgainInvalidNumber(List<String> openPositions) {
        return String.valueOf(openPositions.get(0));
    }

    protected String displayToString(String[] board) {
        String display = "";
        for (String token : board)
            display += token + " ";
        display = display.trim();
        return display;
    }

    @Override
    public String choiceOfPlayerToken(List<String> tokenChoices) {
        this.playerOptions = tokenChoices;
        return tokenChoice();
    }

    @Override
    public void announceGameTerminated() {
        terminatedCalled = true;
    }

    @Override
    public void gameDraw(String[] board) {
        drawCalled = true;
    }

    @Override
    public void gameWin(String winner, String[] board) {
        gameWinner = winner;
    }

    protected abstract int exitAfterTurn();
    protected abstract String nextMove(List<String> openPositions);
    protected abstract String tokenChoice();
}

