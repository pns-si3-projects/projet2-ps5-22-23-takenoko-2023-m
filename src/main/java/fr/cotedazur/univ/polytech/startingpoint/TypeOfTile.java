package fr.cotedazur.univ.polytech.startingpoint;

public enum TypeOfTile {
    RED,
    GREEN,
    YELLOW,
    POND;

    public boolean equals(TypeOfTile type) {
        return this.toString().equals(type.toString());
    }

}
