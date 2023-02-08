package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private Coordinate coordinate;
    private int bamboo = 0;
    private TypeOfTile typeOfTile;
    private boolean isIrrigated = false;
    private TypeOfArrangement typeOfArrangement=TypeOfArrangement.NONE;
    private int key;

    public Tile(Coordinate coordinate, TypeOfTile type){
        this.coordinate = coordinate;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 200000000 + 1);
        this.typeOfTile = type;
        this.typeOfArrangement = TypeOfArrangement.NONE;

        ArrayList<Coordinate> list = getNeighbourCoordinates();
        if(list.contains(new Coordinate(0,0))) isIrrigated = true;
    }
    public Tile(Coordinate coordinate, TypeOfTile type, TypeOfArrangement typeOfArrangement){
        this.coordinate = coordinate;
        this.typeOfTile = type;
        this.typeOfArrangement = typeOfArrangement;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 200000000 + 1);
        ArrayList<Coordinate> list = getNeighbourCoordinates();
        if(list.contains(new Coordinate(0,0))) isIrrigated = true;
    }


    public Tile(int x, int y, TypeOfTile type, TypeOfArrangement typeOfArrangement){
        coordinate = new Coordinate(x, y);
        this.typeOfTile = type;
        this.typeOfArrangement = typeOfArrangement;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 2000000000 + 1);
        ArrayList<Coordinate> list = getNeighbourCoordinates();
        if(list.contains(new Coordinate(0,0))) isIrrigated = true;
    }

    public Tile(TypeOfTile type){
        coordinate = null;
        this.typeOfTile = type;
        this.typeOfArrangement = TypeOfArrangement.NONE;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 2000000 + 1);
    }

    public Tile(TypeOfTile type, TypeOfArrangement typeOfArrangement){
        coordinate = null;
        this.typeOfTile = type;
        this.typeOfArrangement = typeOfArrangement;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 20000000 + 1);
    }


    public Tile(Coordinate coordinate) {
        this.typeOfTile = TypeOfTile.GREEN;
        this.coordinate = coordinate;

        this.typeOfArrangement = TypeOfArrangement.NONE;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 20000000 + 1);
        ArrayList<Coordinate> list = getNeighbourCoordinates();
        if(list.contains(new Coordinate(0,0))) isIrrigated = true;

    }


    public int getCoordinnateX() {
        return coordinate.getX();
    }

    public int getCoordinnateY(){
        return coordinate.getY();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
    public void irrigate() {
        this.isIrrigated = true;
    }
    public boolean isIrrigated() {
        return isIrrigated;
    }

    public void setCoordinate(Coordinate coordinate) {
        if(this.coordinate == null){
            this.coordinate = coordinate;
        }
    }

    //tests to see if the tile to test is neighbour to this tile
    //check coordinate system at : https://www.redblobgames.com/grids/hexagons/#neighbors-axial
    //maybe consider to refactor it to only return Coordinate item
    public boolean isNeighbour (Tile tileToTest) {
        List<Coordinate> neighboursToTest = this.getNeighbourCoordinates();
        for (int i = 0; i < 6; i++) {
            boolean sameX = tileToTest.getCoordinnateX() == neighboursToTest.get(i).getX();
            boolean sameY = tileToTest.getCoordinnateY() == neighboursToTest.get(i).getY();
            if (sameX && sameY) {
                return true;
            }
        }
        return false;
    }

    //scans the available Tiles to move panda and gardener considering boardTiles
    public List<Coordinate> scanAvailableCoordinatesToMove (List<Tile> boardTiles) {
        List<Coordinate> availableCoordinates = new ArrayList<>();

        for (int i = 0; i < boardTiles.size(); i++) {
            boolean isOnSimpleX = this.getCoordinnateX() == boardTiles.get(i).getCoordinnateX();    //simpleX means only the line that only changes on x
            boolean isOnSimpleY = this.getCoordinnateY() == boardTiles.get(i).getCoordinnateY();    //simpleY means only the line that only changes on y
            //complexXY means that the line changes on +X-Y or -X+Y
            boolean isOnComplexXY = (boardTiles.get(i).getCoordinnateX() - this.getCoordinnateX()) == -(boardTiles.get(i).getCoordinnateY() - this.getCoordinnateY());

            //if it is on one of the three lines AND if it is not this coordinate
            if ((isOnSimpleX || isOnSimpleY || isOnComplexXY) && !(boardTiles.get(i).coordinate.equals(this.getCoordinate()))) {
                availableCoordinates.add(boardTiles.get(i).getCoordinate());    //add it to the available coordinates
            }
        }
        return availableCoordinates;
    }

    //returns an array of all the neighbour tiles, whether there is one tile at this place or not
    //the name may not be well-chosen, please feel free to propose a new one
    public ArrayList<Coordinate> getNeighbourCoordinates () {
        ArrayList<Coordinate> neighbourCoordinates = new ArrayList<>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != j) {   //gets rid of (-1, -1), (+0, +0), (+1, +1)
                    int x = coordinate.getX() + i;
                    int y = coordinate.getY() + j;
                    Coordinate neighbourCoordinate = new Coordinate(x, y);
                    neighbourCoordinates.add(neighbourCoordinate);
                }
            }
        }
        /*
        //check the result :
        System.out.println("size : " + neighbourCoordinates.size());
        for (int i = 0; i < 6; i++) {
            System.out.println(neighbourCoordinates.get(i));
        }*/
        return neighbourCoordinates;
    }

    // scans the available tiles to move panda and gardener from this Tile considering the available Tiles in boardTiles
    @Override
    public String toString() {
        return "Tile at x = " + coordinate.getX() + ", y = " + coordinate.getY();
    }

    public void eatBamboo(){
        if(this.getTypeOfArrangement()!= TypeOfArrangement.ENCLOSURE){
            this.bamboo--;
        }
        if (this.bamboo < 0) {
            this.bamboo = 0;
        }

    }

    public TypeOfTile getTypeOfTile(){
        return this.typeOfTile;
    }


    public int grow() {
        if (this.isIrrigated) {
            if (this.getTypeOfArrangement() == TypeOfArrangement.FERTILIZER) {
                this.bamboo += 2;
            } else {
                this.bamboo++;
            }

            if (bamboo > 4) bamboo = 4;

            return bamboo;
        }
        return -1;
    }

    public int getBamboo() {
        return bamboo;
    }

    public void setBamboo(int bamboo) {
        this.bamboo = bamboo;
    }

    public void setTypeOfTile(TypeOfTile typeOfTile) {
        this.typeOfTile = typeOfTile;
    }

    public int getKey(){ return this.key;}

    @Override
    public boolean equals (Object o) {
        if (o != null) {
            if (o instanceof Tile) {
                Tile t = (Tile) o;
                if (t.getKey() == this.getKey()) {
                    return true;
                }
            }
        }
        return false;
    }


    public TypeOfArrangement getTypeOfArrangement() {
        return typeOfArrangement;
    }

    public void setTypeOfArrangement(TypeOfArrangement typeOfArrangement) {
        this.typeOfArrangement = typeOfArrangement;
    }

    public List<Coordinate> getNeighbourCoordinateTogetherWith(Tile tile) {
        List<Coordinate> neighbourCoordinates = this.getNeighbourCoordinates();
        neighbourCoordinates.retainAll(tile.getNeighbourCoordinates());
        return neighbourCoordinates;
    }
}

