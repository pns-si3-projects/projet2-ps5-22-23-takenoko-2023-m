package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Arrays;

public class Irrigation {
    private Tile referencedTile1;   //must be a reference to a tile placed in the board
    private Tile referencedTile2;   //must be a reference to a tile placed in the board

    private TypeOfIrrigation irrigationType;

    public Irrigation(Tile referencedTile1, Tile referencedTile2) {
        //STILL NEEDS TO CHANGE IRRIGATION STATE OF BOTH THE TILES (TODO)
        //TODO check that tiles are neighbours
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

    public ArrayList<Tile> getTiles() {
        return new ArrayList<>(Arrays.asList(referencedTile1,referencedTile2));
    }

    public TypeOfIrrigation getIrrigationType() {
        return irrigationType;
    }

    public ArrayList<Irrigation> getLegalIrrigationPlacement (ArrayList<Tile> boardTiles) {     //needs to get the boardTiles to know which position is legal (if one of the two tiles defining an irrigation does not exist in boardTiles then it is not a legal position for an irrigation
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
                break;
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
                break;
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
