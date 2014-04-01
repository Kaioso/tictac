package org.quadrifacet.tic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
        String choice = readFromPlayer("Choose which token to play as or 'exit': ").toUpperCase();
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
    public void displayGameState(String currentTurn, String[] board, List<String> openPositions) {
        String display = "\nPlayer turn: " + currentTurn + "\n" + boardAsString(board, openPositions);
        printConsoleScreen(display);
    }

    private String boardAsString(String[] board, List<String> openPositions) {
        String display = "";
        List<String> boardView = Arrays.asList(board);
        int j = 1;
        for (int i = 0; i < board.length; i += 3) {
            List<String> row = boardView.subList(i, i + 3);
            String places = "";
            String positionNumbers = "";
            for (String item : row) {
                places += item + " ";
                String position = String.valueOf(j);
                positionNumbers += openPositions.contains(position) ? position + " " : "- ";
                j++;
            }
            display += places + " " + positionNumbers + "\n\n";
        }
        return display;
    }

    @Override
    public String getNextMove(List<String> openPositions) {
        return readFromPlayer("Select next position or 'exit': ");
    }

    @Override
    public String tryAgainInvalidNumber(List<String> openPositions) {
        return readFromPlayer("Position is invalid or already chosen.\nPlease select from the list above or type 'exit' to quit: ");
    }

    @Override
    public void gameDraw(String[] board) {
        System.out.println("Draw!");
    }

    @Override
    public void gameWin(String winner, String[] board) {
        String display = "\nGame Over!\n" + boardAsString(board, new ArrayList<String>());
        printConsoleScreen(display + "\n" + "Winner: " + winner);
    }

    @Override
    public void announceGameTerminated() {
        printConsoleScreen("Exitting game...");
    }
}
