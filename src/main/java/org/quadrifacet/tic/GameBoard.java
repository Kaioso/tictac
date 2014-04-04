package org.quadrifacet.tic;

import java.util.*;

public class GameBoard {
    public static final int DIMENSION = 3;
    private String[] board;
    private String currentTurn;

    public static GameBoard fromString(String toBuild, String nextPlayer) {
        if (toBuild == null || nextPlayer == null || toBuild.split(" ").length != 9)
            throw new InvalidBoard();
        return new GameBoard(toBuild, nextPlayer);
    }

    public GameBoard() {
        this.currentTurn = "X";
        this.board = new String[DIMENSION * DIMENSION];
        for (int i = 0; i < this.board.length; i++)
            board[i] = "-";
    }

    private GameBoard(String nextBoard, String nextTurn) {
        this.board = nextBoard.split(" ");
        this.currentTurn = nextTurn;
    }

    private GameBoard(String[] newBoard, String nextTurn) {
        this.board = newBoard;
        this.currentTurn = nextTurn;
    }

    public void play(int position) {
        if (position < 1 || position > board.length)
            throw new InvalidPosition();
        if (!board[position - 1].equals("-"))
            throw new AlreadyChosen();
        board[position - 1] = currentTurn;
        currentTurn = nextTurn();
    }

    private String nextTurn() {
        return isCrossTurn() ? "O" : "X";
    }

    public GameBoard predictedPlay(int position) {
        if (position < 1 || position > board.length)
            throw new InvalidPosition();
        if (!board[position - 1].equals("-"))
            throw new AlreadyChosen();
        return new GameBoard(boardWithMarkedPosition(position), nextTurn());
    }

    private String[] boardWithMarkedPosition(int position) {
        String[] newBoard = getBoard();
        newBoard[position - 1] = currentTurn;
        return newBoard;
    }

    public String[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    public boolean isCrossTurn() {
        return currentTurn.equals("X");
    }

    public boolean isNaughtTurn() {
        return currentTurn.equals("O");
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public List<Integer> getOpenPositions() {
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++)
            if (positionIsEmpty(i))
                positions.add(i + 1);
        return positions;
    }

    private boolean positionIsEmpty(int i) {
        return board[i].equals("-");
    }

    public List<String[]> rows() {
        return Arrays.asList(
                new String[] { board[0], board[1], board[2] },
                new String[] { board[3], board[4], board[5] },
                new String[] { board[6], board[7], board[8] });
    }

    public List<String[]> columns() {
        return Arrays.asList(
                new String[] { board[0], board[3], board[6] },
                new String[] { board[1], board[4], board[7] },
                new String[] { board[2], board[5], board[8] });
    }

    public List<String[]> diagonals() {
        return Arrays.asList(
                new String[]{board[0], board[4], board[8]},
                new String[]{board[2], board[4], board[6]});
    }

    public static class InvalidBoard extends RuntimeException { }

    public static class InvalidPosition extends RuntimeException { }

    public class AlreadyChosen extends RuntimeException { }
}
