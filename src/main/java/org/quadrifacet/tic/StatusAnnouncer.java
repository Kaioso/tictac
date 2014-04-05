package org.quadrifacet.tic;

public interface StatusAnnouncer {
    void announceGameTitle();
    void displayGameState(State state);
    void announceGameTerminated();
}
