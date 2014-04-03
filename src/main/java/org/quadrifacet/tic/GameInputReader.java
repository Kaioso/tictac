package org.quadrifacet.tic;

import java.util.List;

public interface GameInputReader {
    String choiceOfPlayerToken(List<String> tokenChoices);

    String getNextMove(List<String> openPositions);

    String tryAgainInvalidNumber(List<String> openPositions);
}
