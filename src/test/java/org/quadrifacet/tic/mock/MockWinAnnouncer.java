package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.GameWinAnnouncer;

public class MockWinAnnouncer implements GameWinAnnouncer {
    public boolean gameDrawed = false;
    public boolean crossWon = false;
    public boolean naughtWon = false;

    @Override
    public void announceCrossWon(String[] board) {
        crossWon = true;
    }

    @Override
    public void announceNaughtWon(String[] board) {
        naughtWon = true;
    }

    @Override
    public void announceDraw(String[] board) {
        gameDrawed = true;
    }
}
