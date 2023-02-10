package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;

public interface ObjectiveInterface {
    public String getType();

    public void setType(String type);

    public String toString();

    public int getNbPointsWin();

    public boolean isValid(Bot player, Board b);
    public int getComplexity();





    public void play(Bot player);

}


