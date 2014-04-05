package org.quadrifacet.tic;

import java.util.List;

public interface WinAnnouncer {
    public void announceCrossWon(List<BoardPosition> board);
    public void announceNaughtWon(List<BoardPosition> board);
    public void announceDraw(List<BoardPosition> board);
}
