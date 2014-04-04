package org.quadrifacet.tic;

public class GamePresentation {
    private GameStatusAnnouncer status;
    private GameInputReader reader;
    private GameWinAnnouncer winAnnouncer;

    public GamePresentation(GameStatusAnnouncer status, GameInputReader reader, GameWinAnnouncer winAnnouncer) {
        this.status = status;
        this.reader = reader;
        this.winAnnouncer = winAnnouncer;
    }

    public GameStatusAnnouncer getStatus() {
        return status;
    }

    public void setStatus(GameStatusAnnouncer status) {
        this.status = status;
    }

    public GameInputReader getReader() {
        return reader;
    }

    public void setReader(GameInputReader reader) {
        this.reader = reader;
    }

    public GameWinAnnouncer getWinAnnouncer() {
        return winAnnouncer;
    }

    public void setWinAnnouncer(GameWinAnnouncer winAnnouncer) {
        this.winAnnouncer = winAnnouncer;
    }
}
