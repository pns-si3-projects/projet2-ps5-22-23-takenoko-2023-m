package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Irrigation {
    private Tile referencedTile1;
    private Tile referencedTile2;

    private TypeOfIrrigation irrigationType;

    public Irrigation(Tile referencedTile1, Tile referencedTile2) {
        //STILL NEEDS TO CHANGE IRRIGATION STATE OF BOTH THE TILES (TODO)
        this.referencedTile1 = referencedTile1;
        this.referencedTile2 = referencedTile2;

        //checks for the type of irrigation
        if (referencedTile1.getCoordinate().getY() == referencedTile2.getCoordinate().getY()) {
            irrigationType = TypeOfIrrigation.vertical.vertical;
        } else if (referencedTile1.getCoordinate().getX() == referencedTile2.getCoordinate().getX()) {
            irrigationType = TypeOfIrrigation.fSlash.fSlash;
        } else {
            irrigationType = TypeOfIrrigation.bSlash.bSlash;
        }
    }

    public TypeOfIrrigation getIrrigationType() {
        return irrigationType;
    }

    public ArrayList<Irrigation> getNeighbourIrrigation() {
        switch (irrigationType) {
            case vertical -> {
                return getVerticalNeighbourIrrigation();
            }
            case fSlash -> {
                return getFSlashNeighbourIrrigation();
            }
            case bSlash -> {
                return getBSlashNeighbourIrrigation();
            }
        }
        return null;
    }

    private ArrayList<Irrigation> getVerticalNeighbourIrrigation() {
        return new ArrayList<>();
    }

    private ArrayList<Irrigation> getFSlashNeighbourIrrigation() {
        return new ArrayList<>();
    }

    private ArrayList<Irrigation> getBSlashNeighbourIrrigation() {
        return new ArrayList<>();
    }

}
