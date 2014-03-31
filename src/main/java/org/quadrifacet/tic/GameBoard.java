package org.quadrifacet.tic;

import java.util.*;

public class GameBoard {
    public static final int DIMENSION = 3;
    private String[] board;
    private String currentTurn;
    private String winner = "";

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

    public String getBoardDisplay() {
        String display = "";
        for (String item : board)
            display += item + " ";
        return display.trim();
    }

    public GameBoard play(int position) {
        if (position < 1 || position > board.length)
            throw new InvalidPosition();
        String[] newBoard = boardWithMarkedPosition(position);
        return new GameBoard(newBoard, nextTurn());
    }

    private String[] boardWithMarkedPosition(int position) {
        String[] newBoard = getBoard();
        newBoard[position - 1] = currentTurn;
        return newBoard;
    }

    public String[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    private String nextTurn() {
        return isCrossTurn() ? "O" : "X";
    }

    private boolean isCrossTurn() {
        return currentTurn.equals("X");
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

    public int score() {
        return score(1);
    }

    public int score(int depth) {
        if (isWinFor("O"))
            return -100;
        else if (isWinFor("X"))
            return 100;
        else if (isDraw())
            return 0;
        else {
            List<Integer> results = new ArrayList<Integer>();
            for (Integer position : getOpenPositions())
                results.add(play(position).score(depth + 1));

            Integer score = null;
            for (int i : results)
                if (score == null)
                    score = i;
                else if (isNaughtTurn())
                    score = Math.min(score, i);
                else
                    score = Math.max(score, i);

            return isNaughtTurn() ? score + depth : score - depth;
        }
    }

    public boolean isWinFor(String turn) {
        if (!winner.isEmpty())
            return turn.equals(winner);

        boolean onRightDiagonal = true;
        boolean onLeftDiagonal = true;
        boolean won = false;

        for (int i = 0; i < DIMENSION; i++) {
            won |= (isWinningRow(i, turn) || isWinningColumn(i, turn));
            if (won)
                break;

            onRightDiagonal &= isOnRightDiagonalThisRow(i, turn);
            onLeftDiagonal &= isOnLeftDiagonalThisRow(i, turn);
        }

        won |= (onRightDiagonal || onLeftDiagonal);
        if (won)
            winner = turn;
        return won;
    }

    private boolean isWinningRow(int i, String turn) {
        int subStart = DIMENSION * i;
        for (String col : Arrays.asList(board).subList(subStart, subStart + 3))
            if (!col.equals(turn)) return false;
        return true;
    }

    private boolean isWinningColumn(int i, String turn) {
        for (int row = i; row < board.length; row += DIMENSION)
            if (!board[row].equals(turn)) return false;
        return true;
    }

    private boolean isOnRightDiagonalThisRow(int i, String turn) {
        return board[i + (i * DIMENSION)].equals(turn);
    }

    private boolean isOnLeftDiagonalThisRow(int i, String turn) {
        return board[board.length - (DIMENSION + i * 2)].equals(turn);
    }

    public boolean isDraw() {
        return !isWinFor("X") && !isWinFor("O") && getOpenPositions().isEmpty();
    }

    private boolean isNaughtTurn() {
        return currentTurn.equals("O");
    }

    public int bestMove() {
        List<int[]> results = new ArrayList<int[]>();
        for (Integer position : getOpenPositions())
            results.add(new int[]{position, play(position).score()});

        int[] bestPosition = null;
        for (int[] result : results)
            if (bestPosition == null ||
                    isNaughtTurn() && result[1] < bestPosition[1] ||
                    isCrossTurn() && result[1] > bestPosition[1])
                bestPosition = result;
        return bestPosition[0];
    }

    public static class InvalidBoard extends RuntimeException { }

    public static class InvalidPosition extends RuntimeException { }
}
