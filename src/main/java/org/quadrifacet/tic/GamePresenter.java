package org.quadrifacet.tic;

import java.util.List;

/**
 * Created by Howl on 3/30/2014.
 */
public interface GamePresenter {
    void announceGameTitle();

    String choiceOfPlayerToken(List<String> tokenChoices);

    void displayGameState(String currentTurn, String[] board);

    String getNextMove(List<Integer> openPositions);

    String tryAgainInvalidNumber(List<Integer> openPositions);

    void gameDraw(String[] board);

    void gameWin(String winner, String[] board);

    void announceGameTerminated();
}
