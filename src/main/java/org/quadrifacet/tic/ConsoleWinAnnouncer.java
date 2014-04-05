package org.quadrifacet.tic;

public class ConsoleWinAnnouncer implements WinAnnouncer {
    private Console console;

    public ConsoleWinAnnouncer(Console console) {
        this.console = console;
    }

    @Override
    public void announceCrossWon(State state) {
        console.printConsoleScreen("Winner: X!");
    }

    @Override
    public void announceNaughtWon(State state) {
        console.printConsoleScreen("Winner: O!");
    }

    @Override
    public void announceDraw(State state) {
        console.printConsoleScreen("Draw!");
    }
}
