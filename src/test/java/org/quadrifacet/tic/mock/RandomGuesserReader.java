package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.GameInputReader;

import java.util.List;
import java.util.Random;

public class RandomGuesserReader implements GameInputReader {
    private Random r = new Random();
    private String token;

    public RandomGuesserReader() {
        this.token = "X";
    }

    public RandomGuesserReader(String token) {
        this.token = token;
    }

    @Override
    public String choiceOfPlayerToken(List<String> tokenChoices) {
        return token;
    }

    @Override
    public String getNextMove(List<String> openPositions) {
        return openPositions.get(r.nextInt(openPositions.size()));
    }

    @Override
    public String tryAgainInvalidNumber(List<String> openPositions) {
        return openPositions.get(r.nextInt(openPositions.size()));
    }
}
