package org.quadrifacet.tic;

import java.util.ArrayList;
import java.util.List;

public class State {
    private final boolean isCrossTurn;
    private final List<BoardPosition> board;
    private final List<BoardPosition> openPositions;
    private final LifetimeController controller;

    public State(boolean isCrossTurn, List<BoardPosition> board, LifetimeController controller) {
        this.isCrossTurn = isCrossTurn;
        this.board = board;
        this.controller = controller;
        this.openPositions = getOpen();
    }

    private List<BoardPosition> getOpen() {
        List<BoardPosition> positions = new ArrayList<BoardPosition>();
        for (BoardPosition position : board)
            if (position.isEmptyPosition())
                positions.add(position);
        return positions;
    }

    public boolean isCrossTurn() {
        return isCrossTurn;
    }

    public boolean isNaughtTurn() {
        return !isCrossTurn;
    }

    public List<BoardPosition> getBoard() {
        return board;
    }

    public List<BoardPosition> getOpenPositions() {
        return openPositions;
    }

    public LifetimeController getController() {
        return controller;
    }
}
