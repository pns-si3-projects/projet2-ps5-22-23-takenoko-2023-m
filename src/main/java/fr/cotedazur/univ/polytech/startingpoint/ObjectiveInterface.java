package fr.cotedazur.univ.polytech.startingpoint;

public interface ObjectiveInterface {
    public String getType();
    public void setType(String type);
    public String toString();
    public int getNbPointsWin();
    public boolean isValid(Player player, Board b);
    public TypeOfTile getTypeOfTile();
}


