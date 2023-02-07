package fr.cotedazur.univ.polytech.startingpoint;
import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;
import java.util.ArrayList;
import java.util.logging.*;
import java.util.List;

public class Main {
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String... args) {
        LOGGER.setLevel(Level.INFO);
        Board board = new Board();

        Bot bot1 = new PrimaryBot(board, "Simon");
        Bot bot2 = new PrimaryBot(board, "Damien");


        List<Bot> listPlayer = new ArrayList<>();
        listPlayer.add(bot1);
        listPlayer.add(bot2);
        GameEngine game = new GameEngine(board, listPlayer);
        game.launchGame();

    }
}
