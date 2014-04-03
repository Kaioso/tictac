package org.quadrifacet.tic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Game {
    public static final Pattern EXIT_COMMAND = Pattern.compile("(?i)quit|exit");
    private final GameInputReader reader;
    private final GamePresenter presenter;
    protected GameBoard board;
    private String playerTurn;

    public Game(GamePresenter presenter, GameInputReader reader) {
        this.reader = reader;
        this.presenter = presenter;
        board = new GameBoard();
    }

    public void run() {
        presenter.announceGameTitle();
        runUntilGameOver();
    }

    private void runUntilGameOver() {
        try {
            initializeGame();
            loopGame();
        } catch (GameOver e) {
            if (board.isDraw())
                presenter.gameDraw(board.getBoard());
            else {
                String winner = board.isWinFor("X") ? "X" : "O";
                presenter.gameWin(winner, board.getBoard());
            }
        } catch (GameTerminated e) {
            presenter.announceGameTerminated();
        }
    }

    private void initializeGame() {
        List<String> tokens = Arrays.asList("X", "O");
        String choice = reader.choiceOfPlayerToken(tokens).toUpperCase();
        while (!tokens.contains(choice)) {
            checkExitCommand(choice);
            choice = reader.choiceOfPlayerToken(tokens).toUpperCase();
        }
        playerTurn = choice;
    }

    private void loopGame() {
        String lastTurn = board.getCurrentTurn();
        while (!board.isDraw() && !board.isWinFor(lastTurn)) {
            lastTurn = board.getCurrentTurn();
            presenter.displayGameState(board.getCurrentTurn(), board.getBoard(), movesAsStrings(board.getOpenPositions()));
            int moveNumber = getNextMove(lastTurn);
            board = board.play(moveNumber);
        }
        throw new GameOver();
    }

    private int getNextMove(String lastTurn) {
        if (isPlayerTurn(lastTurn))
            return Integer.parseInt(getUserMove(board));
        else
            return board.bestMove();
    }

    private boolean isPlayerTurn(String lastTurn) {
        return lastTurn.equals(playerTurn);
    }

    private String getUserMove(GameBoard board) {
        List<String> moves = movesAsStrings(board.getOpenPositions());
        String move = reader.getNextMove(moves);
        while (!moves.contains(move)) {
            checkExitCommand(move);
            move = reader.tryAgainInvalidNumber(moves);
        }
        return move;
    }

    private List<String> movesAsStrings(List<Integer> possibleMoves) {
        List<String> movesAsStrings = new ArrayList<String>();
        for (Integer move : possibleMoves)
            movesAsStrings.add(move.toString());
        return movesAsStrings;
    }

    private void checkExitCommand(String move) {
        if (isExitCommand(move))
            throw new GameTerminated();
    }

    private boolean isExitCommand(String move) {
        return EXIT_COMMAND.matcher(move).matches();
    }

    private class GameOver extends RuntimeException {}
    private class GameTerminated extends RuntimeException {}
}
