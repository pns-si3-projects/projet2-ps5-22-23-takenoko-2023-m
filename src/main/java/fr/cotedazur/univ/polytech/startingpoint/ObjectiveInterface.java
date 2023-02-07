package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;

public interface ObjectiveInterface {
    public String getType();
    public void setType(String type);
    public String toString();
    public int getNbPointsWin();
    public boolean isValid(Bot player, Board b);
    public void play(Bot player);
}


