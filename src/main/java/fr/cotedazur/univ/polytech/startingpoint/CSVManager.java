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

    /**
     * Constructor of the class
     * @param nbParties The number of parties
     * @param file The file to write
     * @param listBot The list of bots
     */
    public CSVManager( int nbParties, String file,List<String[]> listBot) {
        this.nbParties = nbParties;
        this.file = file;
        this.listBot = listBot;
    }


    /**
     * Method to write on the csv file
     * @throws IOException
     */
    public void writeCSV() throws IOException{
        CSVWriter writer = new CSVWriter(new FileWriter(file));
        String[] headers = {"Nom", "Nombre_de_victoires","Taux_de_victoire_sur_"+nbParties+"_parties"};
        writer.writeNext(headers);
        for (String[] bot : listBot) {

            writer.writeNext(bot);
        }
        writer.close();
    }

    /**
     * Method to read the csv file
     * @return Read the csv file
     * @throws IOException
     * @throws CsvValidationException
     */
    public List<List<String>> readerCSV() throws IOException, CsvValidationException {
        List<List<String>> list = new ArrayList<>();
        CSVReader reader = null;
        reader = new CSVReader(new FileReader(file));

        String[] line;
        line = reader.readNext();
        List<String> head = new ArrayList<String>();
        if(line==null){
            String[] tmp = {"0", "Nombre_de_victoires","Taux_de_victoire_sur_"+nbParties+"_parties"};
            List<String> h = new ArrayList<String>();
            h.add((String) tmp[0]);
            h.add((String) tmp[1]);
            h.add((String) tmp[2]);
            list.add(h);

            String[] tmp2 = {"Nom", "0.0","0.0%"};
            List<String> h2 = new ArrayList<String>();
            h2.add((String) tmp2[0]);
            h2.add((String) tmp2[1]);
            h2.add((String) tmp2[2]);
            list.add(h2);

            List<String> h3 = new ArrayList<String>();
            h3.add((String) tmp2[0]);
            h3.add((String) tmp2[1]);
            h3.add((String) tmp2[2]);
            list.add(h3);


        }
        else{
            String str = line[2];
            str=str.replaceAll("Taux_de_victoire_sur_", "");
            str=str.replaceAll("_parties", "");
            head.add(str);
            list.add(head);
            while ((line = reader.readNext()) != null) {
                List<String> bot = new ArrayList<String>();
                bot.add((String) line[0]);
                bot.add((String) line[1]);
                bot.add((String) line[2].replaceAll("%", ""));
                list.add(bot);
            }

        }
        reader.close();

        return list;





    }


    /**
     * Setter of the list bot
     * @param listBot The new list bot
     */
    public void setListBot(List<String[]> listBot) {
        this.listBot = listBot;
    }

    /**
     * The getter of the number of parties
     * @return The number of parties
     */
    public int getNbParties() {
        return nbParties;
    }

    /**
     * The setter of the number of parties
     * @param nbParties The new number of parties
     */
    public void setNbParties(int nbParties) {
        this.nbParties = nbParties;
    }
}
