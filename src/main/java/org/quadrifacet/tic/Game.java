package org.quadrifacet.tic;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final InputReader reader;
    private final StatusAnnouncer presenter;
    protected WinAnnouncer winAnnouncer;
    protected VictoryCondition victory;
    protected TicTacAI ai;
    protected Board board;
    protected String playerTurn;

    public Game(StatusAnnouncer presenter, InputReader reader) {
        this.reader = reader;
        this.presenter = presenter;
    }

    public Game(Presentation presentation) {
        this.reader = presentation.getReader();
        this.presenter = presentation.getStatus();
        this.winAnnouncer = presentation.getWinAnnouncer();
    }

    public void run() {
        presenter.announceGameTitle();
        runUntilGameOver();
    }

    private void runUntilGameOver() {
        try {
            initializeGame();
        } catch (GameTerminated e) {
            presenter.announceGameTerminated();
        }
    }

    private void initializeGame() {
        initializeGameActors();

        reader.choosePlayerToken(new TokenSelector() {
            @Override
            public void chooseCross() {
                playerTurn = "X";
                loopGame();
            }

            @Override
            public void chooseNaught() {
                playerTurn = "O";
                loopGame();
            }

            @Override
            public void exitGame() {
                throw new GameTerminated();
            }
        });
    }

    protected void initializeGameActors() {
        this.board = new Board();
        this.victory = new VictoryCondition(board, winAnnouncer);
        this.ai = new TicTacAI(board);
    }

    private void loopGame() {
        while (!victory.winConditionsSatisfied()) {
            presenter.displayGameState(getState());
            IndexedBoardPosition move = getNextMove();
            board.play(move.getIndex());
        }
    }

    private State getState() {
        LifetimeController controller = new LifetimeController() {
            @Override
            public void exitGame() {
                throw new GameTerminated();
            }
        };
        return new State(board.isCrossTurn(), board.getBoard(), controller);
    }

    private IndexedBoardPosition getNextMove() {
        if (isPlayerTurn())
            return getUserMove(board);
        else
            return new IndexedBoardPosition(ai.bestMove());
    }

    private boolean isPlayerTurn() {
        return board.isCrossTurn() && playerTurn.equals("X") ||
                board.isNaughtTurn() && playerTurn.equals("O");
    }

    private IndexedBoardPosition getUserMove(Board board) {
        State state = getState();
        List<BoardPosition> openMoves = state.getOpenPositions();
        IndexedBoardPosition move = (IndexedBoardPosition) reader.getNextPosition(state);
        while (!openMoves.contains(move)) {
            move = (IndexedBoardPosition) reader.tryAgainInvalidMove(getState());
        }
        return move;
    }

    private List<BoardPosition> movesAsBoardPositions(List<Integer> possibleMoves) {
        List<BoardPosition> moves = new ArrayList<BoardPosition>();
        for (Integer move : possibleMoves)
            moves.add(new IndexedBoardPosition(move));
        return moves;
    }

    private class GameTerminated extends RuntimeException {}
}
