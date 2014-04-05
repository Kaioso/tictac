package org.quadrifacet.tic;

import java.util.ArrayList;
import java.util.List;

public class TicTacAI {
    private final Board gameBoard;

    public TicTacAI(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public int score() {
        return score(gameBoard, 1);
    }

    public int score(Board board) {
        return score(board, 1);
    }

    public int score(Board board, int depth) {
        VictoryCondition victoryCondition = new VictoryCondition();
        if (victoryCondition.didNaughtWin(board))
            return -100;
        else if (victoryCondition.didCrossWin(board))
            return 100;
        else if (victoryCondition.isDraw(board))
            return 0;
        else {
            List<Integer> results = new ArrayList<Integer>();
            for (Integer position : board.getOpenPositions())
                results.add(score(board.predictedPlay(position), depth + 1));

            Integer score = null;
            for (int i : results)
                if (score == null)
                    score = i;
                else if (board.isNaughtTurn())
                    score = Math.min(score, i);
                else
                    score = Math.max(score, i);

            return board.isNaughtTurn() ? score + depth : score - depth;
        }
    }

    public int bestMove() {
        List<int[]> results = new ArrayList<int[]>();
        List<BoardPosition> board = gameBoard.getBoard();
        for (int i = 0; i < board.size(); i++)
            if (board.get(i).isEmptyPosition())
                results.add(new int[]{i, score(gameBoard.predictedPlay(i))});

        int[] bestPosition = null;
        for (int[] result : results)
            if (bestPosition == null ||
                    gameBoard.isNaughtTurn() && result[1] < bestPosition[1] ||
                    gameBoard.isCrossTurn() && result[1] > bestPosition[1])
                bestPosition = result;
        return bestPosition[0];
    }
}