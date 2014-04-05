package org.quadrifacet.tic;

public interface InputReader {
    public BoardPosition getNextPosition(State state);
    public BoardPosition tryAgainInvalidMove(State state);
    public void choosePlayerToken(TokenSelector selector);
}
