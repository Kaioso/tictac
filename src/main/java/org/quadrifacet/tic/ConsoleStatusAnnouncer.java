package org.quadrifacet.tic;

import java.util.List;

public class ConsoleStatusAnnouncer implements StatusAnnouncer {

    private final Console console;

    public ConsoleStatusAnnouncer(Console console) {
        this.console = console;
    }

    @Override
    public void announceGameTitle() {
        console.printConsoleScreen("Tic Tac Toe!\n");
    }

    @Override
    public void displayGameState(State state) {
        String display = "\nPlayer turn: " + currentTurn(state) + "\n" + boardAsString(state.getBoard());
        console.printConsoleScreen(display);
    }

    private String currentTurn(State state) {
        return state.isCrossTurn() ? "X" : "O";
    }

    private String boardAsString(List<BoardPosition> board) {
        String display = "";
        int j = 1;
        for (int i = 0; i < board.size(); i += 3) {
            List<BoardPosition> row = board.subList(i, i + 3);
            String places = "";
            String positionNumbers = "";
            for (BoardPosition item : row) {
                places += positionAsString(item) + " ";
                positionNumbers += item.isEmptyPosition() ? String.valueOf(j) + " " : "- ";
            }
            display += places + " " + positionNumbers + "\n\n";
        }
        return display;
    }

    private String positionAsString(BoardPosition item) {
        if (item.isEmptyPosition())
            return "-";
        else
            return item.isCrossPosition() ? "X" : "O";
    }

    @Override
    public void announceGameTerminated() {
        console.printConsoleScreen("Exiting game...");
    }
}
