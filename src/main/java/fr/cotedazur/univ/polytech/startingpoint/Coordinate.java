package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class Coordinate {
    private int x;
    private int y;

    /**
     * A constructor of the class
     * @param x the coordinate x
     * @param y the coordinate y
     */
    public Coordinate (int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * a getter of the coordinate x
     * @return the coordinate x
     */
    public int getX() {
        return this.x;
    }

    /**
     * a getter of the coordinate y
     * @return the coordinate y
     */
    public int getY() {
        return this.y;
    }


    /**
     * A method to find all the neighbour coordinates
     * @return A list of all the neighbour coordinates
     */
    public List<Coordinate> getNeighbourCoordinates () {
        List<Coordinate> neighbourCoordinates = new ArrayList<>();


        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != j) {   //gets rid of (-1, -1), (+0, +0), (+1, +1)
                    int tmpX = x + i;
                    int tmpY = y + j;
                    Coordinate neighbourCoordinate = new Coordinate(tmpX, tmpY);
                    neighbourCoordinates.add(neighbourCoordinate);
                }
            }
        }
        return neighbourCoordinates;
    }

    /**
     * A method to find the number of neighbours
     * @param boardCoordinates The board coordinates
     * @return the number of neighbours
     */
    public int getNumberOfNeighbours(List<Coordinate> boardCoordinates) {
        int nbNeighbours = 0;
        List<Coordinate> closeNeighbours = this.getNeighbourCoordinates();

        for (Coordinate coordinate : boardCoordinates) {
            if (closeNeighbours.contains(coordinate)) {
                nbNeighbours++;
            }
        }

        return nbNeighbours;
    }

    /**
     * equals method
     * @param newCoordinate The coordinate to compare
     * @return True is it's the same, false if it's not
     */
    @Override
    public boolean equals(Object newCoordinate) {
        return newCoordinate instanceof Coordinate toTest &&(this.x == toTest.getX()) && (this.y == toTest.getY());
    }
    @Override
    public int hashCode() {
        return (this.x * 1000) + this.y;
    }

    /**
     * toString method
     * @return a String
     */
    @Override
    public String toString() {
        return "x = " + this.x + ", y = " + this.y;
    }
}
