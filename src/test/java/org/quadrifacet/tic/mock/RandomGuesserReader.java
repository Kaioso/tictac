package org.quadrifacet.tic.mock;

import org.quadrifacet.tic.*;

import java.util.Random;

public class RandomGuesserReader implements InputReader {
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
    public BoardPosition getNextPosition(State state) {
        playerTurnsCounted++;
        if (turnToExitAfter > -1 && turnToExitAfter-- > 0)
            state.getController().exitGame();
        else if (tryGarbage)
            return null;

        return state.getOpenPositions().get(r.nextInt(state.getOpenPositions().size()));
    }

    @Override
    public BoardPosition tryAgainInvalidMove(State state) {
        throw new AttemptedInvalidMove();
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

    public static class AttemptedInvalidMove extends RuntimeException { }
}
