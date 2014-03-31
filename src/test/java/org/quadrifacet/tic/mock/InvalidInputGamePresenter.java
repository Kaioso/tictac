package org.quadrifacet.tic.mock;

import java.util.Arrays;
import java.util.List;

public class InvalidInputGamePresenter extends MockGamePresenter {

    public boolean invalidCalled = false;
    public String turnAfterRedo = "";
    public int selectedItemAfterRedo = -1;

    @Override
    public void displayGameState(String currentTurn, String[] board) {
        if (invalidCalled) {
            turnAfterRedo = currentTurn;
            selectedItemAfterRedo = Arrays.asList(board).indexOf("X") + 1;
        }
    }

    @Override
    protected String tokenChoice() {
        return "X";
    }

    @Override
    protected String nextMove(List<Integer> openPositions) {
        if (invalidCalled)
            return "3";
        else
            return "werljk";
    }

    @Override
    protected int exitAfterTurn() {
        return 2;
    }

    @Override
    public String tryAgainInvalidNumber(List<Integer> openPositions) {
        invalidCalled = true;
        return nextMove(openPositions);
    }
}
