package fr.cotedazur.univ.polytech.startingpoint;


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
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;

public class CSVManager {
    private List<String[]> listBot = new ArrayList<String[]>();
    private int nbParties;
    private String file;


    public CSVManager( int nbParties, String file,List<String[]> listBot) {
        this.nbParties = nbParties;
        this.file = file;
        this.listBot = listBot;
    }



    public void writeCSV() throws IOException{
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        String[] headers = {"Nom", "Nombre_de_victoires","Taux_de_victoire_sur_"+nbParties+"_parties"};
        writer.writeNext(headers);
        for (String[] bot : listBot) {

            writer.writeNext(bot);
        }
        writer.close();
    }

    public List<List<String>> readerCSV() throws IOException, CsvValidationException {
        List<List<String>> list = new ArrayList<List<String>>();
        CSVReader reader = new CSVReader(new FileReader(file));

        String[] line;
        line = reader.readNext();
        List<String> head = new ArrayList<String>();
        String str = line[2];
        str=str.replaceAll("Taux_de_victoire_sur_", "");
        str=str.replaceAll("_parties", "");
        System.out.println(str);
        head.add(str);
        list.add(head);
        while ((line = reader.readNext()) != null) {
            List<String> bot = new ArrayList<String>();
            bot.add((String) line[0]);
            bot.add((String) line[1]);
            bot.add((String) line[2].replaceAll("%", ""));
            list.add(bot);
        }
        return list;



    }



    public void setListBot(List<String[]> listBot) {
        this.listBot = listBot;
    }

    public int getNbParties() {
        return nbParties;
    }

    public void setNbParties(int nbParties) {
        this.nbParties = nbParties;
    }
}
