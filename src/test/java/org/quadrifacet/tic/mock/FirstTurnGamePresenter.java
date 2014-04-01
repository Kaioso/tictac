package org.quadrifacet.tic.mock;

import java.util.List;

public class FirstTurnGamePresenter extends MockGamePresenter {
    public String firstTurnDisplay = "";
    public String firstBoardDisplay = "";

    @Override
    public void displayGameState(String currentTurn, String[] board, List<String> openPositions) {
        firstTurnDisplay = currentTurn;
        firstBoardDisplay = displayToString(board);
    }

    @Override
    protected int exitAfterTurn() {
        return 1;
    }

    @Override
    protected String tokenChoice() {
        return "X";
    }

    @Override
    protected String nextMove(List<String> openPositions) {
        return "";
    }
}
