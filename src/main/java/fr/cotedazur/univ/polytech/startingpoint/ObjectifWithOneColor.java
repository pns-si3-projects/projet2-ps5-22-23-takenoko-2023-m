package fr.cotedazur.univ.polytech.startingpoint;

public class ObjectifWithOneColor {
    public TypeOfTile typeOfTile;

    public ObjectifWithOneColor(TypeOfTile typeOfTile) {
        this.typeOfTile = typeOfTile;
    }
    public TypeOfTile getTypeOfTile() { return this.typeOfTile; }

    private void setTypeOfTile(TypeOfTile typeOfTile) { this.typeOfTile = typeOfTile; }
}
