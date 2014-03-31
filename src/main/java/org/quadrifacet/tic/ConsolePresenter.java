package org.quadrifacet.tic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ConsolePresenter implements GamePresenter {

    @Override
    public void announceGameTitle() {
        System.out.println("Tic Tac Toe!\n");
    }

    @Override
    public String choiceOfPlayerToken(List<String> tokenChoices) {
        String display = "Token choices: ";
        for (String choice : tokenChoices)
            display += choice + ", ";
        printConsoleScreen(display.substring(0, display.length() - 2));
        String choice = readFromPlayer("Choose which token to play as: ");
        while (!tokenChoices.contains(choice))
            choice = readFromPlayer("I don't recognize that token... Choose from a token indicated above: ").toUpperCase();
        return choice;
    }

    private void printConsoleScreen(String s) {
        System.out.println(s);
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
    public void displayGameState(String currentTurn, String[] board) {
        String display = "Player turn: " + currentTurn + "\n" + boardAsString(board);
        printConsoleScreen(display);
    }

    private String boardAsString(String[] board) {
        String display = "";
        for (int i = 0; i < board.length; i++) {
            if (i % 3 == 0)
                display += "\n";
            else if (i != 0)
                display += " ";

            if ("-".equals(board[i]))
                display += String.valueOf(i + 1);
            else
                display += board[i];
        }
        display += "\n";
        return display;
    }

    @Override
    public String getNextMove(List<String> openPositions) {
        return readFromPlayer("Select next position: ");
    }

    @Override
    public String tryAgainInvalidNumber(List<String> openPositions) {
        return readFromPlayer("Invalid choice. Please select from the following list or enter 'exit' to quit: ");
    }

    @Override
    public void gameDraw(String[] board) {
        System.out.println("Draw!");
    }

    @Override
    public void gameWin(String winner, String[] board) {
        String display = boardAsString(board);
        printConsoleScreen(display + "\n" + "Winner: " + winner);
    }

    @Override
    public void announceGameTerminated() {
        printConsoleScreen("Exitting game...");
    }
}
