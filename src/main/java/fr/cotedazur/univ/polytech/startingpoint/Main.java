package fr.cotedazur.univ.polytech.startingpoint;
import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;
import java.util.List;
import com.opencsv.*;
import java.io.FileReader;
import java.net.URL;

public class Main {
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String... args) {
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



    static File file = new File("stats/test.csv");
    public static void main(String... args) throws IOException {

        String file = "stats/gamestats.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(file));

        //Créer un tableau de chaînes pour les en-têtes de colonne
        String[] headers = {"Nom", "Victoires", "NombreParties"};

        //Écrire les en-têtes de colonne dans le fichier
        writer.writeNext(headers);




        Board board = new Board();

        Bot bot1 = new PrimaryBot(board, "Simon");
        Bot bot2 = new PrimaryBot(board, "Damien");


        List<Bot> listPlayer = new ArrayList<>();
        listPlayer.add(bot1);
        listPlayer.add(bot2);
        GameEngine game = new GameEngine(board, listPlayer);
        game.launchGame();
        String[] bot1Info = {bot1.getNom(),""+bot1.getPoint(),"0"};
        String[] bot2Info = {bot2.getNom(),""+bot2.getPoint(),"1"};
        writer.writeNext(bot1Info);
        writer.writeNext(bot2Info);
        writer.close();


    }
}
