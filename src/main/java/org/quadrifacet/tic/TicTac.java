package org.quadrifacet.tic;

/**
 * Hello world!
 *
 */
public class TicTac
{
    public static void main( String[] args )
    {
        Console console = new Console();
        ConsoleStatusAnnouncer p = new ConsoleStatusAnnouncer(console);
        ConsoleReader r = new ConsoleReader(console);
        ConsoleWinAnnouncer w = new ConsoleWinAnnouncer(console);
        Game game = new Game(new Presentation(p, r, w));
        game.run();
    }
}
