package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.GamePresenter;

import java.util.List;

public abstract class MockGamePresenter implements GamePresenter {
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
    public String choiceOfPlayerToken(List<String> tokenChoices) {
        playerOptions = tokenChoices;
        return tokenChoice();
    }

    @Override
    public String getNextMove(List<Integer> openPositions) {
        if (++turnNumber >= exitAfterTurn())
            return "exit";
        else
            return nextMove(openPositions);
    }

    @Override
    public String tryAgainInvalidNumber(List<Integer> openPositions) {
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
    protected abstract String nextMove(List<Integer> openPositions);
    protected abstract String tokenChoice();
}

