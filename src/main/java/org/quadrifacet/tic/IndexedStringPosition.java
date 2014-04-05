package org.quadrifacet.tic;

public class IndexedStringPosition extends IndexedBoardPosition {
    private final String item;

    public IndexedStringPosition(int index, String item) {
        super(index);
        this.item = item;
    }

    @Override
    public boolean isCrossPosition() {
        return "X".equals(item);
    }

    @Override
    public boolean isNaughtPosition() {
        return "O".equals(item);
    }

    @Override
    public boolean isEmptyPosition() {
        return "-".equals(item);
    }
}
