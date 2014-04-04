package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.GameStatusAnnouncer;

import java.util.List;

public class NullStatusAnnouncer implements GameStatusAnnouncer {
    @Override
    public void announceGameTitle() {

    }

    @Override
    public void displayGameState(String currentTurn, String[] board, List<String> openPositions) {

    }

    @Override
    public void gameDraw(String[] board) {

    }

    @Override
    public void gameWin(String winner, String[] board) {

    }

    @Override
    public void announceGameTerminated() {

    }
}
