package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Irrigation {
    private final Tile referencedTile1;   //must be a reference to a tile placed in the board
    private final Tile referencedTile2;   //must be a reference to a tile placed in the board

    private final TypeOfIrrigation irrigationType;

    public Irrigation(Tile referencedTile1, Tile referencedTile2) throws RuntimeException {

        if (!referencedTile1.isNeighbour(referencedTile2)) {
            throw new RuntimeException( "These tiles are not neighbours (" + referencedTile1 + "\t\t" + referencedTile2 + ")" );
        }

        this.referencedTile1 = referencedTile1;
        this.referencedTile2 = referencedTile2;

        this.referencedTile1.irrigate();
        this.referencedTile2.irrigate();

        //checks for the type of irrigation
        if (referencedTile1.getCoordinate().getY() == referencedTile2.getCoordinate().getY()) {
            irrigationType = TypeOfIrrigation.vertical;
        } else if (referencedTile1.getCoordinate().getX() == referencedTile2.getCoordinate().getX()) {
            irrigationType = TypeOfIrrigation.fSlash;
        } else {
            irrigationType = TypeOfIrrigation.bSlash;
        }
    }

    public List<Tile> getTiles() {
        return new ArrayList<>(Arrays.asList(referencedTile1,referencedTile2));
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

    @Override
    public String toString() {
        return "Irrigation between : " + referencedTile1.toString() + "\t\t" + referencedTile2.toString();
    }
}
