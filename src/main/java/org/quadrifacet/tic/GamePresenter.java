package org.quadrifacet.tic;

import java.util.List;

public interface GamePresenter {
    void announceGameTitle();

    String choiceOfPlayerToken(List<String> tokenChoices);

    void displayGameState(String currentTurn, String[] board, List<String> openPositions);

    String getNextMove(List<String> openPositions);

    String tryAgainInvalidNumber(List<String> openPositions);

    void gameDraw(String[] board);

    void gameWin(String winner, String[] board);

    void announceGameTerminated();
}
