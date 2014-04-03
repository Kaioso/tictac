package org.quadrifacet.tic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ConsoleReader implements GameInputReader {
    private final Console console;

    public ConsoleReader(Console console) {
        this.console = console;
    }

    @Override
    public String choiceOfPlayerToken(List<String> tokenChoices) {
        String display = "Token choices: ";
        for (String choice : tokenChoices)
            display += choice + ", ";
        console.printConsoleScreen(display.substring(0, display.length() - 2));
        String choice = readFromPlayer("Choose which token to play as or 'exit': ").toUpperCase();
        return choice;
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
    public String getNextMove(List<String> openPositions) {
        return readFromPlayer("Select next position or 'exit': ");
    }

    @Override
    public String tryAgainInvalidNumber(List<String> openPositions) {
        return readFromPlayer("Position is invalid or already chosen.\nPlease select from the list above or type 'exit' to quit: ");
    }
}