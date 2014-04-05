package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.BoardPosition;
import org.quadrifacet.tic.WinAnnouncer;

import java.util.List;

public class MockWinAnnouncer implements WinAnnouncer {
    public boolean gameDrawed = false;
    public boolean crossWon = false;
    public boolean naughtWon = false;

    @Override
    public void announceCrossWon(List<BoardPosition> board) {
        crossWon = true;
    }

    @Override
    public void announceNaughtWon(List<BoardPosition> board) {
        naughtWon = true;
    }

    @Override
    public void announceDraw(List<BoardPosition> board) {
        gameDrawed = true;
    }
}
