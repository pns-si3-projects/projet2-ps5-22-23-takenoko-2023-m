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
        if (parameters.csv) {
            list = csvManager.readerCSV();
        }

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
        if (parameters.allBots) {

            nbParties = 250;

            LOGGER.setLevel(Level.SEVERE);
            LOGGER.severe("Run mode : allBots");
        }

        csvManager.setNbParties(nbParties);

        double victoireBot1=0;
        double victoireBot2=0;
        double victoireBot3=0;
        List<String[]> listBot = new ArrayList<>();

        Bot bot1 = new IntermediateBot(new Board(), "Intermédiaire");
        Bot bot2 = new LittleBot(new Board(), "PetitsObjectifs");
        Bot bot3 = new SkynetBot(new Board(), "BotFeature3");


        if(parameters.allBots){
            LOGGER.setLevel(Level.SEVERE);
        }
        else{
            LOGGER.severe("Lancement de " + nbParties + " partie(s) avec des bots intermédiaires vs debutants");
        }

        for(int i=0;i<nbParties;i++){
            listBot.clear();
            Board board = new Board();

            bot1 = new IntermediateBot(board, "Intermédiaire");
            bot2 = new LittleBot(board, "PetitsObjectifs");
            bot3 = new SkynetBot(new Board(), "BotFeature3");

            List<Bot> listPlayer = new ArrayList<>();
            listPlayer.add(bot1);
            listPlayer.add(bot2);
            if(parameters.allBots){
                listPlayer.add(bot3);
            }
            GameEngine game = new GameEngine(board, listPlayer);
            game.launchGame();
            if(!(parameters.allBots)){
                if (bot1.getPoint()>bot2.getPoint()) {
                    victoireBot1++;
                } else if(bot1.getPoint()<bot2.getPoint()){
                    victoireBot2++;
                }
            }
            else{
                if (bot1.getPoint()>bot2.getPoint() && bot1.getPoint()>bot3.getPoint()) {
                    victoireBot1++;
                } else if(bot1.getPoint()<bot2.getPoint() && bot2.getPoint()>bot3.getPoint()){
                    victoireBot2++;
                }
                else if(bot1.getPoint()<bot3.getPoint() && bot2.getPoint()<bot3.getPoint()){
                    victoireBot3++;
                }
            }



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
            String[] bot3Info = {bot3.getNom(),""+victoireBot3,""+(victoireBot3/nbParties)*100+"%"};
            listBot.add(bot1Info);
            listBot.add(bot2Info);
            if(parameters.allBots){
                listBot.add(bot3Info);
            }
        }

        int victoireB1 = (int) victoireBot1;
        int victoireB2 = (int) victoireBot2;
        int victoireB3 = (int) victoireBot3;
        Double egalites = (double) (nbParties - victoireB1 - victoireB2 - victoireB3);
        LOGGER.severe(bot1.getNom() + " a remporté " + victoireB1 + " partie(s) ce qui fait un pourcentage de " + (victoireBot1/nbParties)*100 + "%");
        LOGGER.severe(bot2.getNom() + " a remporté " + victoireB2 + " partie(s) ce qui fait un pourcentage de " + (victoireBot2/nbParties)*100 + "%");
        if(parameters.allBots){
            LOGGER.severe(bot3.getNom() + " a remporté " + victoireB3 + " partie(s) ce qui fait un pourcentage de " + (victoireBot3/nbParties)*100 + "%");
        }
        LOGGER.severe("Il y a eu " + egalites + " égalité(s), ce qui représente " + (egalites/nbParties)*100 + "% des parties");


        if (parameters.twoThousands) {
            LOGGER.severe("Lancement de " + nbParties + " partie(s) avec 2 bots intermédiaire");

            victoireBot1=0;
            victoireBot2=0;
            for(int i=0;i<nbParties;i++){

                Board board = new Board();

                bot1 = new IntermediateBot(board, "Intermediaire1");
                bot2 = new IntermediateBot(board, "Intermediaire2");


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
            egalites = (double) (nbParties - victoireB1 - victoireB2);
            LOGGER.severe(bot1.getNom() + " a remporté " + victoireB1 + " partie(s) ce qui fait un pourcentage de " + (victoireBot1/nbParties)*100 + "%");
            LOGGER.severe(bot2.getNom() + " a remporté " + victoireB2 + " partie(s) ce qui fait un pourcentage de " + (victoireBot2/nbParties)*100 + "%");
            LOGGER.severe("Il y a eu " + egalites + " égalité(s), ce qui représente " + (egalites/nbParties)*100 + "% des parties");
        }



        csvManager.setListBot(listBot);
        csvManager.setNbParties(nbParties);
        csvManager.writeCSV();





    }
}
