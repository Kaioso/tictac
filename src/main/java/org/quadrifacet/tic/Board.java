package org.quadrifacet.tic;

import java.util.*;

public class Board {
    public static final int DIMENSION = 3;
    private String[] board;
    private String currentTurn;

    public static Board fromString(String toBuild, String nextPlayer) {
        if (toBuild == null || nextPlayer == null || toBuild.split(" ").length != 9)
            throw new InvalidBoard();
        return new Board(toBuild, nextPlayer);
    }

    public Board() {
        this.currentTurn = "X";
        this.board = new String[DIMENSION * DIMENSION];
        for (int i = 0; i < this.board.length; i++)
            board[i] = "-";
    }

    private Board(String nextBoard, String nextTurn) {
        this.board = nextBoard.split(" ");
        this.currentTurn = nextTurn;
    }

    private Board(String[] newBoard, String nextTurn) {
        this.board = newBoard;
        this.currentTurn = nextTurn;
    }

    public void play(int position) {
        if (isValidPosition(position))
            throw new InvalidPosition();
        if (!positionIsEmpty(position))
            throw new AlreadyChosen();
        board[position] = currentTurn;
        currentTurn = nextTurn();
    }

    public Board predictedPlay(int position) {
        if (isValidPosition(position))
            throw new InvalidPosition();
        if (!positionIsEmpty(position))
            throw new AlreadyChosen();
        return new Board(boardWithMarkedPosition(position), nextTurn());
    }

    private boolean isValidPosition(int position) {
        return position < 0 || position > board.length;
    }

    private boolean positionIsEmpty(int i) {
        return board[i].equals("-");
    }

    private String nextTurn() {
        return isCrossTurn() ? "O" : "X";
    }

    private String[] boardWithMarkedPosition(int position) {
        String[] newBoard = copyOfBoard();
        newBoard[position] = currentTurn;
        return newBoard;
    }

    public List<BoardPosition> getBoard() {
        List<BoardPosition> positions = new ArrayList<BoardPosition>();
        for (int i = 0; i < board.length; i++)
            positions.add(new IndexedStringPosition(i, board[i]));
        return positions;
    }

    private String[] copyOfBoard() {
        return Arrays.copyOf(board, board.length);
    }

    public boolean isCrossTurn() {
        return currentTurn.equals("X");
    }

    public boolean isNaughtTurn() {
        return currentTurn.equals("O");
    }

    public List<Integer> getOpenPositions() {
        List<Integer> positions = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++)
            if (positionIsEmpty(i))
                positions.add(i);
        return positions;
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
