package fr.cotedazur.univ.polytech.startingpoint;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.*;
import java.io.FileReader;
import com.opencsv.exceptions.CsvValidationException;

public class CSVManager {
    private List<String[]> listBot = new ArrayList<>();
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
        List<List<String>> list = new ArrayList<>();
        CSVReader reader = null;
        try{
            reader = new CSVReader(new FileReader(file));

            String[] line;
            line = reader.readNext();
            List<String> head = new ArrayList<>();
            String str = line[2];
            str=str.replace("Taux_de_victoire_sur_", "");
            str=str.replace("_parties", "");
            head.add(str);
            list.add(head);
            while ((line = reader.readNext()) != null) {
                List<String> bot = new ArrayList<>();
                bot.add(line[0]);
                bot.add(line[1]);
                bot.add(line[2].replace("%", ""));
                list.add(bot);
            }
        }
        catch(Exception e){
            Main.LOGGER.severe("Erreur lors de la lecture du fichier CSV");
        }
        finally {
            if(reader!=null){
                reader.close();
            }
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
