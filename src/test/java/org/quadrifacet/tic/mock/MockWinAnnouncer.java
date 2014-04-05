package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.State;
import org.quadrifacet.tic.WinAnnouncer;

public class MockWinAnnouncer implements WinAnnouncer {
    public boolean gameDrawed = false;
    public boolean crossWon = false;
    public boolean naughtWon = false;

    @Override
    public void announceCrossWon(State state) {
        crossWon = true;
    }

    @Override
    public void announceNaughtWon(State state) {
        naughtWon = true;
    }

    @Override
    public void announceDraw(State state) {
        gameDrawed = true;
    }
}
