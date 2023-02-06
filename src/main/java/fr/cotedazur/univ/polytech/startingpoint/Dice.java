package fr.cotedazur.univ.polytech.startingpoint;

public class Dice {
    public Dice() {
        this.meteo = Meteo.NONE;
    }

    public Meteo getMeteo() {
        return meteo;
    }

    public void setMeteo(Meteo meteo) {
        this.meteo = meteo;
    }

    private Meteo meteo = Meteo.NONE;



    public void randomMeteo(){
        int random = (int) (Math.random() * 6);
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
