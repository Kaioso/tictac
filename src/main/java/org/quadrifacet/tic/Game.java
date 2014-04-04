package org.quadrifacet.tic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Game {
    public static final Pattern EXIT_COMMAND = Pattern.compile("(?i)quit|exit");
    private final GameInputReader reader;
    private final GameStatusAnnouncer presenter;
    protected GameWinAnnouncer winAnnouncer;
    protected VictoryCondition victory;
    protected TicTacAI ai;
    protected GameBoard board;
    private String playerTurn;

    public Game(GameStatusAnnouncer presenter, GameInputReader reader) {
        this.reader = reader;
        this.presenter = presenter;
    }

    public Game(GamePresentation pr) {
        this.reader = pr.getReader();
        this.presenter = pr.getStatus();
        this.winAnnouncer = pr.getWinAnnouncer();
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
            if (victory.isDraw())
                presenter.gameDraw(board.getBoard());
            else {
                String winner = victory.didCrossWin() ? "X" : "O";
                presenter.gameWin(winner, board.getBoard());
            }
        } catch (GameTerminated e) {
            presenter.announceGameTerminated();
        }
    }

    private void initializeGame() {
        initializeGameActors();
        List<String> tokens = Arrays.asList("X", "O");
        String choice = reader.choiceOfPlayerToken(tokens).toUpperCase();
        while (!tokens.contains(choice)) {
            checkExitCommand(choice);
            choice = reader.choiceOfPlayerToken(tokens).toUpperCase();
        }
        playerTurn = choice;
    }

    protected void initializeGameActors() {
        this.board = new GameBoard();
        this.victory = new VictoryCondition(board, winAnnouncer);
        this.ai = new TicTacAI(board);
    }

    private void loopGame() {
        while (!victory.winConditionsSatisfied()) {
            presenter.displayGameState(board.getCurrentTurn(), board.getBoard(), movesAsStrings(board.getOpenPositions()));
            int moveNumber = getNextMove();
            board.play(moveNumber);
        }
        throw new GameOver();
    }



    private int getNextMove() {
        if (isPlayerTurn())
            return Integer.parseInt(getUserMove(board));
        else
            return ai.bestMove();
    }

    private boolean isPlayerTurn() {
        return board.isCrossTurn() && playerTurn.equals("X");
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
