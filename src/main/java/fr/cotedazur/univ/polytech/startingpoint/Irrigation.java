package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Irrigation {


    private final Tile referencedTile1;   //must be a reference to a tile placed in the board
    private final Tile referencedTile2;   //must be a reference to a tile placed in the board
    private final Coordinate coordinate1;
    private final Coordinate coordinate2;

    private final TypeOfIrrigation irrigationType;

    public Irrigation(Tile referencedTile1, Tile referencedTile2) throws RuntimeException {

        if (!referencedTile1.isNeighbour(referencedTile2)) {
            throw new RuntimeException( "These tiles are not neighbours (" + referencedTile1 + "\t\t" + referencedTile2 + ")" );
        }

        this.referencedTile1 = referencedTile1;
        this.referencedTile2 = referencedTile2;

        this.referencedTile1.irrigate();
        this.referencedTile2.irrigate();

        this.coordinate1 = referencedTile1.getCoordinate();
        this.coordinate2 = referencedTile2.getCoordinate();

        this.irrigationType = this.detectTypeOfIrrigation();
    }

    public Irrigation (Coordinate coordinate1, Coordinate coordinate2) throws RuntimeException {
        ArrayList<Coordinate> coordinate1Neighbours = coordinate1.getNeighbourCoordinates();
        if (!coordinate1Neighbours.contains(coordinate2)) {
            throw new RuntimeException("Tiles are not neighbours");
        }

        this.coordinate1 = coordinate1;
        this.coordinate2 = coordinate2;

        this.referencedTile1 = null;
        this.referencedTile2 = null;

        this.irrigationType = this.detectTypeOfIrrigation();
    }

    private TypeOfIrrigation detectTypeOfIrrigation() {
        if (coordinate1.getY() == coordinate2.getY()) {
            return TypeOfIrrigation.vertical;
        } else if (coordinate1.getX() == coordinate2.getX()) {
                return TypeOfIrrigation.fSlash;
        } else {
            return TypeOfIrrigation.bSlash;
        }
    }

    public List<Tile> getTiles() {
        return new ArrayList<>(Arrays.asList(referencedTile1,referencedTile2));
    }

    public List<Coordinate> getCoordinates() {
        return new ArrayList<>(Arrays.asList(coordinate1,coordinate2));
    }

    public TypeOfIrrigation getIrrigationType() {
        return irrigationType;
    }

    public List<Irrigation> getLegalIrrigationPlacement (List<Tile> boardTiles) {     //needs to get the boardTiles to know which position is legal (if one of the two tiles defining an irrigation does not exist in boardTiles then it is not a legal position for an irrigation
        ArrayList<Tile> tilesForIrrigationNeighbours = new ArrayList<>();
        switch (irrigationType) {
            case vertical -> {
                Tile tmpTopTile = new Tile(new Coordinate(referencedTile2.getCoordinnateX(),referencedTile2.getCoordinnateY()-1));      //the Tile being the "top" neighbour of both the tiles of the irrigation (in my representation of the board)
                Tile tmpBotTile = new Tile(new Coordinate(referencedTile1.getCoordinnateX(), referencedTile1.getCoordinnateY()+1));     //the Tile being the "bottom" neighbour of both the tiles of the irrigation (in my representation of the board)
                if (boardTiles.contains(tmpTopTile)) {
                    tilesForIrrigationNeighbours.add(tmpTopTile);
                }
                if (boardTiles.contains(tmpBotTile)) {
                    tilesForIrrigationNeighbours.add(tmpBotTile);
                }
            }
            case fSlash -> {
                Tile tmpLeftTile = new Tile(new Coordinate(referencedTile2.getCoordinnateX()-1, referencedTile2.getCoordinnateY()));        //the Tile being the "bottom left" neighbour of both the tiles of the irrigation (in my representation of the board)
                Tile tmpRightTile = new Tile(new Coordinate(referencedTile1.getCoordinnateX() + 1, referencedTile1.getCoordinnateY()));     //the Tile being the "top right" neighbour of both the tiles of the irrigation (in my representation of the board)
                if (boardTiles.contains(tmpLeftTile)) {
                    tilesForIrrigationNeighbours.add(tmpLeftTile);
                }
                if (boardTiles.contains(tmpRightTile)) {
                    tilesForIrrigationNeighbours.add(tmpRightTile);
                }
            }
            case bSlash -> {
                Tile tmpLeftTile = new Tile(new Coordinate(referencedTile1.getCoordinnateX(), referencedTile2.getCoordinnateY()));      //the Tile being the "top left" neighbour of both the tiles of the irrigation (in my representation of the board)
                Tile tmpRightTile = new Tile(new Coordinate(referencedTile2.getCoordinnateX(), referencedTile1.getCoordinnateY()));     //the Tile being the "bottom right" neighbour of both the tiles of the irrigation (in my representation of the board)
                if (boardTiles.contains(tmpLeftTile)) {
                    tilesForIrrigationNeighbours.add(tmpLeftTile);
                }
                if (boardTiles.contains(tmpRightTile)) {
                    tilesForIrrigationNeighbours.add(tmpRightTile);
                }
            }
        }

        ArrayList<Irrigation> legalNeighbours = new ArrayList<>();
        for (int i = 0 ; i < tilesForIrrigationNeighbours.size(); i++) {
            legalNeighbours.add(new Irrigation(boardTiles.get(boardTiles.indexOf(tilesForIrrigationNeighbours.get(i))), referencedTile1));  //adds an irrigation that gets the reference of the neighbour Tile from boardTiles and one of the tiles of this Irrigation
            legalNeighbours.add(new Irrigation(boardTiles.get(boardTiles.indexOf(tilesForIrrigationNeighbours.get(i))), referencedTile2));  //adds an irrigation that gets the reference of the neighbour Tile from boardTiles and one of the tiles of this Irrigation
        }

        return legalNeighbours;
    }

    public ArrayList<Irrigation> getNeighbourIrrigations() {
        ArrayList<Irrigation> neighbours = new ArrayList<>();
        switch (irrigationType) {
            case vertical -> {
                Irrigation topNeighbour = new Irrigation(coordinate2, new Coordinate(coordinate2.getX(), coordinate2.getY()-1));
                Irrigation botNeighbour = new Irrigation(coordinate1, new Coordinate(coordinate1.getX(), coordinate1.getY()+1));
                neighbours.add(topNeighbour);
                neighbours.add(botNeighbour);
            }
            case fSlash -> {
                Irrigation leftNeighbour = new Irrigation(coordinate2, new Coordinate(coordinate2.getX()-1, coordinate2.getY()));
                Irrigation rightNeighbour = new Irrigation(coordinate1, new Coordinate(coordinate1.getX()+1, coordinate2.getY()));
                neighbours.add(leftNeighbour);
                neighbours.add(rightNeighbour);
            }
            case bSlash -> {
                Irrigation leftIrrigation = new Irrigation(coordinate1, new Coordinate(coordinate1.getX(), coordinate2.getY()));
                Irrigation rightIrrigation = new Irrigation(coordinate2, new Coordinate(coordinate2.getX(), coordinate1.getY()));
                neighbours.add(leftIrrigation);
                neighbours.add(rightIrrigation);
            }
        }
        return neighbours;
    }

    public boolean isNeighbour (Irrigation potentialNeighbour) {
        ArrayList<Irrigation> thisNeighbours = this.getNeighbourIrrigations();
        if (thisNeighbours.contains(potentialNeighbour)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Irrigation between : " + coordinate1.toString() + "\t\t" + coordinate2.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Irrigation) {
                Irrigation i = (Irrigation) o;
                if ((this.coordinate1.equals(i.getCoordinates().get(0))) && (this.coordinate2.equals(i.getCoordinates().get(1)))) {
                    return true;
                }
            }
        }
        return false;
    }
}
