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
