package org.quadrifacet.tic;

import java.util.List;

public class VictoryCondition {

    public boolean didCrossWin(Board gameBoard) {
        return isWinFor("X", gameBoard);
    }

    public boolean didNaughtWin(Board gameBoard) {
        return isWinFor("O", gameBoard);
    }

    public boolean isDraw(Board gameBoard) {
        return !didCrossWin(gameBoard) && !didNaughtWin(gameBoard) && gameBoard.getOpenPositions().isEmpty();
    }

    private boolean isWinFor(String turn, Board gameBoard) {

        boolean won = false;
        List<String[]> currentRows = gameBoard.rows();
        List<String[]> currentColumns = gameBoard.columns();
        List<String[]> currentDiagonals = gameBoard.diagonals();
        for (int i = 0; i < Board.DIMENSION; i++) {
            boolean row = true;
            boolean col = true;
            boolean diag = true;
            for (int c = 0; c < Board.DIMENSION; c++) {
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