package fr.cotedazur.univ.polytech.startingpoint;
import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;
import java.util.ArrayList;
import java.util.logging.*;
import java.util.List;

public class Main {
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String... args) {
        System.out.println(args[0]);
        if (args.length > 0) {
            if (args[0].equals("--demo")) {
                System.out.println("mode de démo");     //affichage complet
            }

            if (args[0].equals("--2thousands")) {
                System.out.println("mode statistiques");    //aucun affichage sauf à la fin des 1000 parties
                LOGGER.setLevel(Level.SEVERE);
            }
            if (args[0].equals("--csv")) {
                System.out.println("mode csv");    //aucun affichage sauf à la fin des parties
                LOGGER.setLevel(Level.SEVERE);
            }
        } else {
            LOGGER.setLevel(Level.SEVERE);
        }




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
