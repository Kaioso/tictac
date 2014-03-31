package org.quadrifacet.tic;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Game {
    public static final Pattern EXIT_COMMAND = Pattern.compile("(?i)quit|exit");
    private GamePresenter presenter;
    protected GameBoard board;
    private String playerTurn;

    public Game(GamePresenter presenter) {
        this.presenter = presenter;
        board = new GameBoard();
    }

    public void run() {
        presenter.announceGameTitle();
        playerTurn = presenter.choiceOfPlayerToken(Arrays.asList("X", "O", "Random"));
        runUntilGameOver();
    }

    private void runUntilGameOver() {
        try {
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

    private void loopGame() {
        String lastTurn = board.getCurrentTurn();
        while (!board.isDraw() && !board.isWinFor(lastTurn)) {
            lastTurn = board.getCurrentTurn();
            presenter.displayGameState(board.getCurrentTurn(), board.getBoard());

            String move;
            if (isPlayerTurn(lastTurn))
                move = getUserMove(board);
            else
                move = String.valueOf(board.bestMove());
            int moveNumber = Integer.parseInt(move);
            board = board.play(moveNumber);
        }
        throw new GameOver();
    }

    private boolean isPlayerTurn(String lastTurn) {
        return lastTurn.equals(playerTurn);
    }

    private String getUserMove(GameBoard board) {
        List<Integer> possibleMoves = board.getOpenPositions();
        String move = presenter.getNextMove(possibleMoves);
        while (notNumeric(move)) {
            checkExitCommand(move);
            move = presenter.tryAgainInvalidNumber(possibleMoves);
        }
        return move;
    }

    private void checkExitCommand(String move) {
        if (isExitCommand(move))
            throw new GameTerminated();
    }

    private boolean isExitCommand(String move) {
        return EXIT_COMMAND.matcher(move).matches();
    }

    private boolean notNumeric(String move) {
        boolean isNumeric = true;
        for (char m : move.toCharArray())
            isNumeric &= Character.isDigit(m);
        return not(isNumeric);
    }

    private boolean not(boolean statement) {
        return !statement;
    }

    private class GameOver extends RuntimeException {}
    private class GameTerminated extends RuntimeException {}
}
