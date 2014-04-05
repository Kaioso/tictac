package org.quadrifacet.tic;

public class IndexedBoardPosition implements BoardPosition {
    protected final int index;

    public IndexedBoardPosition(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean isCrossPosition() {
        return false;
    }

    @Override
    public boolean isNaughtPosition() {
        return false;
    }

    @Override
    public boolean isEmptyPosition() {
        return true;
    }
}
