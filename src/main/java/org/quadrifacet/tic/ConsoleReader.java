package org.quadrifacet.tic;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleReader implements InputReader {
    private final Console console;

    public ConsoleReader(Console console) {
        this.console = console;
    }

    public String readFromPlayer(String prompt) {
        System.out.print(prompt);
        if (System.console() != null) {
            return System.console().readLine();
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (Exception e) {
            return "exit";
        }
    }

    @Override
    public BoardPosition getNextPosition(State state) {
        return null;
    }

    @Override
    public BoardPosition tryAgainInvalidMove(State state) {
        return null;
    }

    @Override
    public void choosePlayerToken(TokenSelector selector) {

    }
}