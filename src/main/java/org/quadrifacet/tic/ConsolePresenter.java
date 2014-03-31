package org.quadrifacet.tic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class ConsolePresenter implements GamePresenter {

    @Override
    public void announceGameTitle() {
        System.out.println("Tic Tac Toe!\n\n");
    }

    @Override
    public String choiceOfPlayerToken(List<String> tokenChoices) {
        int menuIndex = 1;
        for (String choice : tokenChoices)
            System.out.println(String.valueOf(menuIndex++) + " - " + choice + "\n");
        System.out.print("Choose which token to play as: ");
        int input = Integer.parseInt(readFromPlayer());
        return tokenChoices.get(input - 1);
    }

    public String readFromPlayer() {
        if (System.console() != null) {
            return System.console().readLine();
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "exit";
        }
    }

    @Override
    public void displayGameState(String currentTurn, String[] board) {
        System.out.println("Player turn: " + currentTurn + "\n\n");
        String display = "";
        for (int i = 0; i < board.length; i++) {
            if (i % 3 == 0)
                display += "\n";
            display += board[i];
        }
        display += "\n";
        System.out.println(display);
    }

    @Override
    public String getNextMove(List<Integer> openPositions) {
        String display = "";
        for (Integer pos : openPositions) {
            display += pos.toString() + " ";
        }
        System.out.println("Select next position:\n");
        System.out.println(display.trim());
        return readFromPlayer();
    }

    @Override
    public String tryAgainInvalidNumber(List<Integer> openPositions) {
        String display = "";
        for (Integer pos : openPositions) {
            display += pos.toString() + " ";
        }
        System.out.println("Invalid choice. Please select from the following list or enter 'exit' to quit:\n");
        System.out.println(display.trim());
        return readFromPlayer();
    }

    @Override
    public void gameDraw(String[] board) {
        System.out.println("Draw!\n");
    }

    @Override
    public void gameWin(String winner, String[] board) {
        String display = "";
        for (int i = 0; i < board.length; i++) {
            if (i % 3 == 0)
                display += "\n";
            display += board[i];
        }
        display += "\n\n";
        System.out.println(display);

        System.out.println("Winner: " + winner + "\n");
    }

    @Override
    public void announceGameTerminated() {
        System.out.println("Exitting game...");
    }
}
