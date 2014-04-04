package org.quadrifacet.tic.mock;


import java.util.List;

public class InvalidTokenPresenter extends MockGamePresenter {
    private boolean firstTurnDone = false;
    public boolean tokenChoiceCalledOnce = false;
    public String eventualToken = "";

    @Override
    protected int exitAfterTurn() {
        return 2;
    }

    @Override
    protected String nextMove(List<String> openPositions) {
        return openPositions.get(0);
    }

    @Override
    protected String tokenChoice() {
        if (tokenChoiceCalledOnce)
            return "O";
        else {
            tokenChoiceCalledOnce = true;
            return "bacons";
        }
    }

    @Override
    public void displayGameState(String currentTurn, String[] board, List<String> openPositions) {
        if (firstTurnDone && eventualToken.isEmpty())
            eventualToken = currentTurn;
        else
            firstTurnDone = true;
    }
}
