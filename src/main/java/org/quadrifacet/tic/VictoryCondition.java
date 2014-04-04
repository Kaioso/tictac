package org.quadrifacet.tic;

import java.util.List;

public class VictoryCondition {
    private final GameBoard gameBoard;
    String winner = "";

    public VictoryCondition(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public boolean didCrossWin() {
        return isWinFor("X");
    }

    public boolean didNaughtWin() {
        return isWinFor("O");
    }

    public boolean isDraw() {
        return !didCrossWin() && !didNaughtWin() && gameBoard.getOpenPositions().isEmpty();
    }

    private boolean isWinFor(String turn) {
        if (!winner.isEmpty())
            return turn.equals(winner);

        boolean won = false;
        List<String[]> currentRows = gameBoard.rows();
        List<String[]> currentColumns = gameBoard.columns();
        List<String[]> currentDiagonals = gameBoard.diagonals();
        for (int i = 0; i < GameBoard.DIMENSION; i++) {
            boolean row = true;
            boolean col = true;
            boolean diag = true;
            for (int c = 0; c < GameBoard.DIMENSION; c++) {
                row &= turn.equals(currentRows.get(i)[c]);
                col &= turn.equals(currentColumns.get(i)[c]);
                if (i < currentDiagonals.size())
                    diag &= turn.equals(currentDiagonals.get(i)[c]);
                else
                    diag = false;
            }
            won |= row || col || diag;
            if (won)
                break;
        }
        return won;
    }
}