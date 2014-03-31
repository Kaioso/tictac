package org.quadrifacet.tic;

/**
 * Hello world!
 *
 */
public class TicTac
{
    public static void main( String[] args )
    {
        Game game = new Game(new ConsolePresenter());
        game.run();
    }
}
