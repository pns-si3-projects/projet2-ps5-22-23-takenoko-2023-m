package fr.cotedazur.univ.polytech.startingpoint;
import com.opencsv.exceptions.CsvException;
import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.IntermediateBot;
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
import com.beust.jcommander.JCommander;

public class Main {
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static File file = new File("stats/test.csv");


    public static void main(String... args) throws IOException, CsvException {

        //String file = "stats/gamestats.csv";
        //CSVWriter writer = new CSVWriter(new FileWriter(file));

        //Créer un tableau de chaînes pour les en-têtes de colonne
        //String[] headers = {"Nom", "Victoires", "NombreParties"};

        //Écrire les en-têtes de colonne dans le fichier
        //writer.writeNext(headers);



        Args parameters = new Args();
        JCommander.newBuilder()
                .addObject(parameters)
                .build()
                .parse(args);

        CSVManager csvManager = new CSVManager(1, "stats/gamestats.csv", new ArrayList<String[]>());

        int nbParties = 1;
        if (parameters.twoThousands) {
            System.out.println("2000");
            nbParties = 1000;
            LOGGER.setLevel(Level.SEVERE);
        }
        if (parameters.demo) {
            System.out.println("demo");
            LOGGER.setLevel(Level.INFO);
        }
        if (parameters.csv) {
            System.out.println("csv");
            nbParties = 1000;
            LOGGER.setLevel(Level.SEVERE);
        }

        csvManager.setNbParties(nbParties);

        double victoireBot1=0;
        double victoireBot2=0;
        List<String[]> listBot = new ArrayList<>();
        Bot bot1 = new IntermediateBot(new Board(), "Simon");
        Bot bot2 = new IntermediateBot(new Board(), "Damien");



        for(int i=0;i<nbParties;i++){
            listBot.clear();
            Board board = new Board();

            bot1 = new IntermediateBot(board, "Simon");
            bot2 = new IntermediateBot(board, "Damien");


            List<Bot> listPlayer = new ArrayList<>();
            listPlayer.add(bot1);
            listPlayer.add(bot2);
            GameEngine game = new GameEngine(board, listPlayer);
            game.launchGame();
            if (bot1.getPoint()>bot2.getPoint()) {
                victoireBot1++;
            } else {
                victoireBot2++;
            }

            String[] bot1Info = {bot1.getNom(),""+victoireBot1,""+(victoireBot1/(i+1))*100+"%"};
            String[] bot2Info = {bot2.getNom(),""+victoireBot2,""+(victoireBot2/(i+1))*100+"%"};
            listBot.add(bot1Info);
            listBot.add(bot2Info);
            csvManager.setListBot(listBot);
            csvManager.setNbParties(i+1);
            csvManager.writeCSV();
        }

        listBot.clear();
        String[] bot1Info = {bot1.getNom(),""+victoireBot1,""+(victoireBot1/nbParties)*100+"%"};
        String[] bot2Info = {bot2.getNom(),""+victoireBot2,""+(victoireBot2/nbParties)*100+"%"};
        listBot.add(bot1Info);
        listBot.add(bot2Info);


        if (parameters.twoThousands) {
            victoireBot1=0;
            victoireBot2=0;
            for(int i=0;i<nbParties;i++){

                Board board = new Board();

                bot1 = new PrimaryBot(board, "Simon");
                bot2 = new PrimaryBot(board, "Damien");


                List<Bot> listPlayer = new ArrayList<>();
                listPlayer.add(bot1);
                listPlayer.add(bot2);
                GameEngine game = new GameEngine(board, listPlayer);
                game.launchGame();
                if (bot1.getPoint()>bot2.getPoint()) {
                    victoireBot1++;
                } else {
                    victoireBot2++;
                }



            }
            String[] bot12Info = {bot1.getNom(),""+victoireBot1,""+(victoireBot1/(nbParties))*100+"%"};
            String[] bot22Info = {bot2.getNom(),""+victoireBot2,""+(victoireBot2/(nbParties))*100+"%"};
            String[] blanc = {"","",""};
            String[] header2 = {"Nom", "Nombre_de_victoires","Taux_de_victoire_sur_"+nbParties+"_parties"};
            listBot.add(blanc);
            listBot.add(header2);
            listBot.add(bot12Info);
            listBot.add(bot22Info);

        }



        csvManager.setListBot(listBot);
        csvManager.setNbParties(nbParties);
        csvManager.writeCSV();


        //String[] bot1Info = {bot1.getNom(),""+bot1.getPoint(),"0"};
        //String[] bot2Info = {bot2.getNom(),""+bot2.getPoint(),"1"};
        //writer.writeNext(bot1Info);
        //writer.writeNext(bot2Info);


    }
}
