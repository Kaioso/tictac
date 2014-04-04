package org.quadrifacet.tic;

import java.util.List;

public interface GameStatusAnnouncer {
    void announceGameTitle();
    void displayGameState(String currentTurn, String[] board, List<String> openPositions);
    void gameDraw(String[] board);
    void gameWin(String winner, String[] board);
    void announceGameTerminated();
}
