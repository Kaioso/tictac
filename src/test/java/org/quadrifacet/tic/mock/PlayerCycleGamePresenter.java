package org.quadrifacet.tic.mock;

import java.util.List;

public class PlayerCycleGamePresenter extends MockGamePresenter {
    public boolean didNotCycle = false;
    private String lastTurn = "";

    @Override
    protected int exitAfterTurn() {
        return 3;
    }

    @Override
    protected String nextMove(List<String> openPositions) {
        return openPositions.get(0).toString();
    }

    @Override
    protected String tokenChoice() {
        return "X";
    }

    @Override
    public void displayGameState(String currentTurn, String[] board) {
        lastTurn = currentTurn;
    }

    @Override
    public String getNextMove(List<String> openPositions) {
        if (!"X".equals(lastTurn))
            didNotCycle = true;
        return super.getNextMove(openPositions);
    }
}
