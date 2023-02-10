package fr.cotedazur.univ.polytech.startingpoint;
import com.opencsv.exceptions.CsvException;
import fr.cotedazur.univ.polytech.startingpoint.bots.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;
import java.util.List;
import com.beust.jcommander.JCommander;

public class Main <T>{
    public final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static void main(String... args) throws IOException, CsvException {

        Args parameters = new Args();
        JCommander.newBuilder()
                .addObject(parameters)
                .build()
                .parse(args);

        CSVManager csvManager = new CSVManager(1, "stats/gamestats.csv", new ArrayList<String[]>());
        List<List<String>> list = new ArrayList<List<String>>();
        list = csvManager.readerCSV();


        int nbParties = 1;
        if (parameters.twoThousands) {
            nbParties = 1000;
            LOGGER.setLevel(Level.SEVERE);
            LOGGER.severe("Run mode : two thousand games");
        }
        if (parameters.demo) {
            LOGGER.setLevel(Level.INFO);
            LOGGER.severe("Run mode : demo");
        }
        if (parameters.csv) {

            nbParties = 10;

            LOGGER.setLevel(Level.SEVERE);
            LOGGER.severe("Run mode : csv");
        }

        csvManager.setNbParties(nbParties);

        double victoireBot1=0;
        double victoireBot2=0;
        List<String[]> listBot = new ArrayList<>();

        Bot bot1 = new IntermediateBot(new Board(), "Simon");
        Bot bot2 = new LittleBot(new Board(), "Damien");



        LOGGER.severe("Lancement de " + nbParties + " partie(s) avec des bots intermédiaires vs debutants");

        for(int i=0;i<nbParties;i++){
            listBot.clear();
            Board board = new Board();

            bot1 = new IntermediateBot(board, "Simon");
            bot2 = new LittleBot(board, "Damien");



            List<Bot> listPlayer = new ArrayList<>();
            listPlayer.add(bot1);
            listPlayer.add(bot2);
            GameEngine game = new GameEngine(board, listPlayer);
            game.launchGame();
            if (bot1.getPoint()>bot2.getPoint()) {
                victoireBot1++;
            } else if(bot1.getPoint()<bot2.getPoint()){
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
        if(parameters.csv){
            nbParties += Integer.parseInt(list.get(0).get(0));
            victoireBot1 += Double.parseDouble(list.get(1).get(1));
            victoireBot2 += Double.parseDouble(list.get(2).get(1));
            String[] bot1Info = {bot1.getNom(),""+victoireBot1,""+(victoireBot1/nbParties)*100+"%"};
            String[] bot2Info = {bot2.getNom(),""+victoireBot2,""+(victoireBot2/nbParties)*100+"%"};
            listBot.add(bot1Info);
            listBot.add(bot2Info);
        }
        else{
            String[] bot1Info = {bot1.getNom(),""+victoireBot1,""+(victoireBot1/nbParties)*100+"%"};
            String[] bot2Info = {bot2.getNom(),""+victoireBot2,""+(victoireBot2/nbParties)*100+"%"};
            listBot.add(bot1Info);
            listBot.add(bot2Info);
        }

        int victoireB1 = (int) victoireBot1;
        int victoireB2 = (int) victoireBot2;
        LOGGER.severe(bot1.getNom() + " a remporté " + victoireB1 + " partie(s) ce qui fait un pourcentage de " + (victoireBot1/nbParties)*100 + "%");
        LOGGER.severe(bot2.getNom() + " a remporté " + victoireB2 + " partie(s) ce qui fait un pourcentage de " + (victoireBot2/nbParties)*100 + "%");


        if (parameters.twoThousands) {
            LOGGER.severe("Lancement de " + nbParties + " partie(s) avec 2 bots intermédiaire");

            victoireBot1=0;
            victoireBot2=0;
            for(int i=0;i<nbParties;i++){

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



            }
            String[] bot12Info = {bot1.getNom(),""+victoireBot1,""+(victoireBot1/(nbParties))*100+"%"};
            String[] bot22Info = {bot2.getNom(),""+victoireBot2,""+(victoireBot2/(nbParties))*100+"%"};
            String[] blanc = {"","",""};
            String[] header2 = {"Nom", "Nombre_de_victoires","Taux_de_victoire_sur_"+nbParties+"_parties"};
            listBot.add(blanc);
            listBot.add(header2);
            listBot.add(bot12Info);
            listBot.add(bot22Info);

            victoireB1 = (int) victoireBot1;
            victoireB2 = (int) victoireBot2;
            LOGGER.severe(bot1.getNom() + " a remporté " + victoireB1 + " partie(s) ce qui fait un pourcentage de " + (victoireBot1/nbParties)*100 + "%");
            LOGGER.severe(bot2.getNom() + " a remporté " + victoireB2 + " partie(s) ce qui fait un pourcentage de " + (victoireBot2/nbParties)*100 + "%");

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
