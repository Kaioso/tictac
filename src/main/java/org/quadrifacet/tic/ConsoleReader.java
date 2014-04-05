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
        return getPlayerMoveChoice(state, "Select next position or 'exit': ");
    }

    @Override
    public BoardPosition tryAgainInvalidMove(State state) {
        return getPlayerMoveChoice(state, "You must select a non-empty position from the list above" +
                "\nChoose next position or 'exit': ");
    }

    private BoardPosition getPlayerMoveChoice(State state, String prompt) {
        String choice = null;
        while (choice == null || !isNumber(choice)) {
            choice = readFromPlayer(prompt);
            checkForExit(state.getController(), choice);
        }
        int move = Integer.parseInt(choice) - 1;
        return state.getBoard().get(move);
    }

    private void checkForExit(LifetimeController controller, String choice) {
        if (choice.matches("(?i)exit|quit")) {
            controller.exitGame();
        }
    }

    private boolean isNumber(String choice) {
        boolean is = true;
        for (char c : choice.toCharArray())
            is &= Character.isDigit(c);
        return is;
    }

    @Override
    public void choosePlayerToken(TokenSelector selector) {
        String choice = null;
        while (choice == null || !choice.matches("[xXoO]")) {
            choice = readFromPlayer("Please choose which token you wish to play (X or O): ");
            checkForExit(selector, choice);
        }

        if ("X".equals(choice.toUpperCase()))
            selector.chooseCross();
        else if("O".equals(choice.toUpperCase()))
            selector.chooseNaught();
        else
            selector.exitGame();
    }
}