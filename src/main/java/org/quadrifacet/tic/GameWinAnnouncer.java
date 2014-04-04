package org.quadrifacet.tic;

public interface GameWinAnnouncer {
    public void announceCrossWon(String[] board);
    public void announceNaughtWon(String[] board);
    public void announceDraw(String[] board);
}
