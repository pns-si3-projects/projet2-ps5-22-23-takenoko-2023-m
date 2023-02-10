package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tile {
    private Coordinate coordinate;
    private int bamboo = 0;
    private TypeOfTile typeOfTile;
    private boolean isIrrigated = false;
    private TypeOfArrangement typeOfArrangement=TypeOfArrangement.NONE;
    private int key;

    /**
     * A construtor of the class
     * @param coordinate The coordinate of the tile
     * @param type The type of the tile
     * @param typeOfArrangement The typeOfArrangement
     */
    public Tile(Coordinate coordinate, TypeOfTile type, TypeOfArrangement typeOfArrangement){
        this.coordinate = coordinate;
        this.typeOfTile = type;
        this.typeOfArrangement = typeOfArrangement;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 200000000 + 1);
        if(this.coordinate != null) {
            List<Coordinate> list = getNeighbourCoordinates();
            if (list.contains(new Coordinate(0, 0))) isIrrigated = true;
        }
    }
/*
    public Tile(Coordinate coordinate, TypeOfTile type){
        this.coordinate = coordinate;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 200000000 + 1);
        this.typeOfTile = type;
        this.typeOfArrangement = TypeOfArrangement.NONE;

        List<Coordinate> list = getNeighbourCoordinates();
        if(list.contains(new Coordinate(0,0))) isIrrigated = true;
    }*/
/*
    public Tile(int x, int y, TypeOfTile type, TypeOfArrangement typeOfArrangement){
        coordinate = new Coordinate(x, y);
        this.typeOfTile = type;
        this.typeOfArrangement = typeOfArrangement;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 2000000000 + 1);
        List<Coordinate> list = getNeighbourCoordinates();
        if(list.contains(new Coordinate(0,0))) isIrrigated = true;
    }*/
/*-
    public Tile(TypeOfTile type){
        coordinate = null;
        this.typeOfTile = type;
        this.typeOfArrangement = TypeOfArrangement.NONE;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 2000000 + 1);
    }*/
/*
    public Tile(TypeOfTile type, TypeOfArrangement typeOfArrangement){
        coordinate = null;
        this.typeOfTile = type;
        this.typeOfArrangement = typeOfArrangement;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 20000000 + 1);
    }*/

/*
    public Tile(Coordinate coordinate) {
        this.typeOfTile = TypeOfTile.GREEN;
        this.coordinate = coordinate;

        this.typeOfArrangement = TypeOfArrangement.NONE;
        //Random key from 1 to 20000
        this.key = (int)(Math.random() * 20000000 + 1);
        List<Coordinate> list = getNeighbourCoordinates();
        if(list.contains(new Coordinate(0,0))) isIrrigated = true;

    }*/

    /**
     * Getter of the coordinate x
     * @return the coordinate x
     */
    public int getCoordinnateX() {
        return coordinate.getX();
    }

    /**
     * Getter of the coordinate y
     * @return the coordinate y
     */
    public int getCoordinnateY(){
        return coordinate.getY();
    }

    /**
     * getter of the coordinate
     * @return the coordinate of the class
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * A method to irrigate the tile
     */
    public void irrigate() {
        this.isIrrigated = true;
    }

    /**
     * A method to find if the tile is irrigated
     * @return true if it's irrigated, false if it's not
     */
    public boolean isIrrigated() {
        return isIrrigated;
    }

    /**
     * A setter of the coordinate
     * @param coordinate the new coordinate
     */
    public void setCoordinate(Coordinate coordinate) {
        if(this.coordinate == null){
            this.coordinate = coordinate;
        }
    }


    /**
     * A method to find if 2 tiles are neighbours
     * @param tileToTest The tile to test
     * @return true if the tiles are neighbours, false if it's not
     */
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

    /**
     * A method to find all available coordinates to move from the current tile
     * @param boardTiles A list of the board Tiles
     * @return A list of all legal coordinates
     */
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

    /**
     * A method which return the neighbour coordinates of the current tile
     * @return A list of the neighbour coordinates
     */
    public List<Coordinate> getNeighbourCoordinates () {
        List<Coordinate> neighbourCoordinates = new ArrayList<>();

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
        return neighbourCoordinates;
    }

    /**
     * toString method
     * @return a String
     */
    @Override
    public String toString() {
        return "Tile at x = " + coordinate.getX() + ", y = " + coordinate.getY();
    }

    /**
     * A method which decrements the number of bamboo of the tile
     */
    public void eatBamboo(){
        if(this.getTypeOfArrangement()!= TypeOfArrangement.ENCLOSURE){
            this.bamboo--;
        }
        if (this.bamboo < 0) {
            this.bamboo = 0;
        }

    }

    /**
     * A getter of the type of tile
     * @return the type of tile
     */
    public TypeOfTile getTypeOfTile(){
        return this.typeOfTile;
    }

    /**
     * A method to grow the bamboo on the tile
     * @return the number of bamboo on the tile
     */
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

    /**
     * A getter of the number of bamboo
     * @return the number of bamboo
     */
    public int getBamboo() {
        return bamboo;
    }

    /**
     * a setter of the number of bamboo
     * @param bamboo the new number of bamboo
     */
    public void setBamboo(int bamboo) {
        this.bamboo = bamboo;
    }

    /**
     * A setter of the typeOfTile
     * @param typeOfTile the new TypeOfTile
     */
    public void setTypeOfTile(TypeOfTile typeOfTile) {
        this.typeOfTile = typeOfTile;
    }

    /**
     * A getter of the key
     * @return the key
     */
    public int getKey(){ return this.key;}

    /**
     * Equals method
     * @param o The object to test
     * @return true if it's equals, false if it's not
     */
    @Override
    public boolean equals (Object o) {
        if (o instanceof Tile t && t.getKey() == this.getKey()) {
            return true;

        }
        return false;
    }

    /**
     * hashCode method
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordinate, bamboo, typeOfTile, isIrrigated, typeOfArrangement);
    }

    /**
     * a getter of the type of arrangement
     * @return the type of arrangement
     */
    public TypeOfArrangement getTypeOfArrangement() {
        return typeOfArrangement;
    }

    /**
     * a setter of the type of arrangement
     * @param typeOfArrangement the new type of arrangement
     */
    public void setTypeOfArrangement(TypeOfArrangement typeOfArrangement) {
        this.typeOfArrangement = typeOfArrangement;
    }

    /**
     * a method to get the neighbour coordinate with another
     * @param tile The tile to test
     * @return A list of coordinates
     */
    public List<Coordinate> getNeighbourCoordinateTogetherWith(Tile tile) {
        List<Coordinate> neighbourCoordinates = this.getNeighbourCoordinates();
        neighbourCoordinates.retainAll(tile.getNeighbourCoordinates());
        return neighbourCoordinates;
    }
}

