package org.quadrifacet.tic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsolePresenter implements GamePresenter {

    private final Console console;

    public ConsolePresenter(Console console) {
        this.console = console;
    }

    @Override
    public void announceGameTitle() {
        console.printConsoleScreen("Tic Tac Toe!\n");
    }

    @Override
    public void displayGameState(String currentTurn, String[] board, List<String> openPositions) {
        String display = "\nPlayer turn: " + currentTurn + "\n" + boardAsString(board, openPositions);
        console.printConsoleScreen(display);
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
    public void gameDraw(String[] board) {
        console.printConsoleScreen("Draw!");
    }

    @Override
    public void gameWin(String winner, String[] board) {
        String display = "\nGame Over!\n" + boardAsString(board, new ArrayList<String>());
        console.printConsoleScreen(display + "\n" + "Winner: " + winner);
    }

    @Override
    public void announceGameTerminated() {
        console.printConsoleScreen("Exitting game...");
    }
}
