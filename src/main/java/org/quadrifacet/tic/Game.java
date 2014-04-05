package org.quadrifacet.tic;

import java.util.List;

public class Game {
    private final InputReader reader;
    private final StatusAnnouncer presenter;
    protected WinAnnouncer winAnnouncer;
    protected VictoryCondition victory;
    protected TicTacAI ai;
    protected Board board;
    protected String playerTurn;

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
        this.victory = new VictoryCondition();
        this.ai = new TicTacAI(board);
    }

    private void loopGame() {
        while (!winConditionSatisfied()) {
            presenter.displayGameState(getState());
            IndexedBoardPosition move = getNextMove();
            board.play(move.getIndex());
        }
    }

    private boolean winConditionSatisfied() {
        State state = getState();
        if (victory.isDraw(board))
            winAnnouncer.announceDraw(state);
        else if (victory.didCrossWin(board))
            winAnnouncer.announceCrossWon(state);
        else if (victory.didNaughtWin(board))
            winAnnouncer.announceNaughtWon(state);
        else
            return false;
        return true;
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
            return getUserMove();
        else
            return new IndexedBoardPosition(ai.bestMove());
    }

    private boolean isPlayerTurn() {
        return board.isCrossTurn() && playerTurn.equals("X") ||
                board.isNaughtTurn() && playerTurn.equals("O");
    }

    private IndexedBoardPosition getUserMove() {
        State state = getState();
        List<BoardPosition> openMoves = state.getOpenPositions();
        IndexedBoardPosition move = (IndexedBoardPosition) reader.getNextPosition(state);
        while (!openMoves.contains(move)) {
            move = (IndexedBoardPosition) reader.tryAgainInvalidMove(getState());
        }
        return move;
    }

    private class GameTerminated extends RuntimeException {}
}
