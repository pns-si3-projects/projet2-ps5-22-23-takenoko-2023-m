package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Tile {
    private final Coordinate coordinate;
    private int bamboo = 0;
    public Tile(int x, int y){
    private int bamboo = 0;
    private String typeOfTile;
    public Tile(int x, int y, String type){
        coordinate = new Coordinate(x, y);
        this.typeOfTile = type;
    }

    public Tile(int x, int y){ coordinate = new Coordinate(x,y); }

    public int getCoordinnateX() {
        return coordinate.getX();
    }

    public int getCoordinnateY(){
        return coordinate.getY();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    //tests to see if the tile to test is neighbour to this tile
    //check coordinate system at : https://www.redblobgames.com/grids/hexagons/#neighbors-axial
    //maybe consider to refactor it to only return Coordinate item
    public boolean isNeighbour (Tile tileToTest) {
        ArrayList<Coordinate> neighboursToTest = this.getNeighbourCoordinates();
        for (int i = 0; i < 6; i++) {
            boolean sameX = tileToTest.getCoordinnateX() == neighboursToTest.get(i).getX();
            boolean sameY = tileToTest.getCoordinnateY() == neighboursToTest.get(i).getY();
            if (sameX && sameY) {
                return true;
            }
        }
        return false;
    }

    //returns an array of all the neighbour tiles, whether there is one tile at this place or not
    //the name may not be well-chosen, please feel free to propose a new one
    public ArrayList<Coordinate> getNeighbourCoordinates () {
        /*
        ArrayList<Tile> neighbourCoordinates = new ArrayList<>();   //this ArrayList gets all the neighbour position of this tile
        //attribution of neighbourCoordinates
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != j) {   //gets rid of (-1, -1), (+0, +0), (+1, +1)
                    int xToTest = coordinate.getX() + i;
                    int yToTest = coordinate.getY() + j;
                    Tile potentialNeighbour = new Tile(xToTest, yToTest);
                    neighbourCoordinates.add(potentialNeighbour);
                }
            }

         */
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


    @Override
    public String toString() {
        String str = "Tile at x = " + coordinate.getX() + ", y = " + coordinate.getY();
        return str;
    }

//fonction temporaire destinée à être remplacée
    public boolean isNeighbor(Tile tile){
        if(tile.getCoordinnateX() == this.getCoordinnateX() && tile.getCoordinnateY() == this.getCoordinnateY()+1){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX() && tile.getCoordinnateY() == this.getCoordinnateY()-1){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX()+1 && tile.getCoordinnateY() == this.getCoordinnateY()){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX()-1 && tile.getCoordinnateY() == this.getCoordinnateY()){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX()+1 && tile.getCoordinnateY() == this.getCoordinnateY()-1){
            return true;
        }
        if(tile.getCoordinnateX() == this.getCoordinnateX()-1 && tile.getCoordinnateY() == this.getCoordinnateY()+1){
            return true;
        }
        return false;
    }


    public void eatBamboo(){
        this.bamboo--;
    }

    public String getTypeOfTile(){
        return this.typeOfTile;
    }


    public int grow(int i) {
        bamboo+=i;
        if(bamboo>4) bamboo =4;
        return bamboo;
    }

    public int getBamboo() {
        return bamboo;
    }

    public void setBamboo(int bamboo) {
        this.bamboo = bamboo;
    }
}

