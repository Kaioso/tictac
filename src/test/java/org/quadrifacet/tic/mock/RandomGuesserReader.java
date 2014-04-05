package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.GameInputReader;
import org.quadrifacet.tic.TokenSelector;

import java.util.List;
import java.util.Random;

public class RandomGuesserReader implements GameInputReader {
    private Random r = new Random();
    private int turnToExitAfter = -1;
    private boolean tryGarbage = false;
    private boolean crossShouldBeChosen = true;

    public int playerTurnsCounted = 0;

    @Override
    public void choosePlayerToken(TokenSelector selector) {
        if (crossShouldBeChosen)
            selector.chooseCross();
        else
            selector.chooseNaught();
    }

    @Override
    public String getNextMove(List<String> openPositions) {
        playerTurnsCounted++;
        if (turnToExitAfter > -1 && turnToExitAfter-- > 0)
            return "exit";
        else if (tryGarbage)
            return "sdfj230fmc";
        else
            return openPositions.get(r.nextInt(openPositions.size()));
    }

    @Override
    public String tryAgainInvalidNumber(List<String> openPositions) {
        throw new AttemptedInvalidNumber();
    }

    public RandomGuesserReader exitAfterTurn(int turn) {
        this.turnToExitAfter = turn;
        return this;
    }

    public RandomGuesserReader guessGarbageAndThrowException() {
        this.tryGarbage = true;
        return this;
    }

    public RandomGuesserReader chooseNaught() {
        crossShouldBeChosen = false;
        return this;
    }

    public static class AttemptedInvalidNumber extends RuntimeException {
    }
}
