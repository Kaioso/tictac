package org.quadrifacet.tic;

public interface WinAnnouncer {
    public void announceCrossWon(State state);
    public void announceNaughtWon(State state);
    public void announceDraw(State state);
}
