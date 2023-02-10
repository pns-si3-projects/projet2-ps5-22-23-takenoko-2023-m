package fr.cotedazur.univ.polytech.startingpoint;

public class ObjectifWithOneColor {
    /** The type of the objective*/
    public TypeOfTile typeOfTile;

    /**
     * A constructor of the class
     * @param typeOfTile The type of the objective
     */
    public ObjectifWithOneColor(TypeOfTile typeOfTile) {
        this.typeOfTile = typeOfTile;
    }

    /**
     * A getter of the type of the objective
     * @return
     */
    public TypeOfTile getTypeOfTile() { return this.typeOfTile; }

    /**
     * A setter of the new type of the objective
     * @param typeOfTile The new type of the objective
     */
    private void setTypeOfTile(TypeOfTile typeOfTile) { this.typeOfTile = typeOfTile; }
}
