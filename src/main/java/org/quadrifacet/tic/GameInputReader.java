package org.quadrifacet.tic;

import java.util.List;

public interface GameInputReader {
    String getNextMove(List<String> openPositions);
    String tryAgainInvalidNumber(List<String> openPositions);
    void choosePlayerToken(TokenSelector selector);
}
