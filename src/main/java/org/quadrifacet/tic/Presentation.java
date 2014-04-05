package org.quadrifacet.tic;

public class Presentation {
    private StatusAnnouncer status;
    private InputReader reader;
    private WinAnnouncer winAnnouncer;

    public Presentation(StatusAnnouncer status, InputReader reader, WinAnnouncer winAnnouncer) {
        this.status = status;
        this.reader = reader;
        this.winAnnouncer = winAnnouncer;
    }

    public StatusAnnouncer getStatus() {
        return status;
    }
    public void setStatus(StatusAnnouncer status) {
        this.status = status;
    }
    public InputReader getReader() {
        return reader;
    }
    public void setReader(InputReader reader) {
        this.reader = reader;
    }
    public WinAnnouncer getWinAnnouncer() {
        return winAnnouncer;
    }
    public void setWinAnnouncer(WinAnnouncer winAnnouncer) {
        this.winAnnouncer = winAnnouncer;
    }
}
