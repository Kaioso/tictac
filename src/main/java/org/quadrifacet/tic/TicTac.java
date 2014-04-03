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
        ConsolePresenter p = new ConsolePresenter(console);
        ConsoleReader r = new ConsoleReader(console);
        Game game = new Game(p, r);
        game.run();
    }
}
