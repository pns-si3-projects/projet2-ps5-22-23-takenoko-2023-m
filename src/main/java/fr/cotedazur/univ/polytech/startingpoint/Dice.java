package fr.cotedazur.univ.polytech.startingpoint;

import java.util.Random;

public class Dice {
    private Random rand = new Random();

    /**
     * The constructor of the class
     */
    public Dice() {
        this.meteo = Meteo.NONE;
    }

    /**
     * The getter of the meteo
     * @return the meteo
     */
    public Meteo getMeteo() {
        return meteo;
    }

    /**
     * A setter of the new meteo
     * @param meteo The new meteo
     */
    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }

    private Meteo meteo = Meteo.NONE;


    /**
     * A method to generate a random meteo
     */
    public void randomMeteo(){
        int random = -1;
        for(int i=0;i<10;i++){
            random = rand.nextInt(6);
        }


        switch (random) {
            case 0 -> this.meteo = Meteo.RAIN;
            case 1 -> this.meteo = Meteo.SUN;
            case 2 -> this.meteo = Meteo.QUESTIONMARK;
            case 3 -> this.meteo = Meteo.LIGHTNING;
            case 4 -> this.meteo = Meteo.WIND;
            case 5 -> this.meteo = Meteo.CLOUD;
            default -> this.meteo = Meteo.NONE;
        }
    }

}
