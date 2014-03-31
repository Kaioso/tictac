package org.quadrifacet.tic.mock;

import java.util.Arrays;
import java.util.List;

public class SecondTurnGamePresenter extends MockGamePresenter {
    public String secondTurnDisplay = "";
    public int selectedBoardPosition = -1;


    @Override
    public void displayGameState(String currentTurn, String[] board) {
        if (turnNumber == 1) {
            secondTurnDisplay = currentTurn;
            selectedBoardPosition = Arrays.asList(board).indexOf("X") + 1;
        }
    }

    @Override
    protected String tokenChoice() {
        return "X";
    }

    @Override
    protected String nextMove(List<Integer> openPositions) {
        return "4";
    }

    @Override
    protected int exitAfterTurn() {
        return 2;
    }
}