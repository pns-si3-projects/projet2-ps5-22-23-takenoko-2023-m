package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    //returns an array of all the neighbour tiles, whether there is one tile at this place or not
    //the name may not be well-chosen, please feel free to propose a new one
    public ArrayList<Coordinate> getNeighbourCoordinates () {
        ArrayList<Coordinate> neighbourCoordinates = new ArrayList<>();

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


    public int getNumberOfNeighbours(List<Coordinate> boardCoordinates) {
        int nbNeighbours = 0;
        ArrayList<Coordinate> closeNeighbours = this.getNeighbourCoordinates();

        for (Coordinate coordinate : boardCoordinates) {
            if (closeNeighbours.contains(coordinate)) {
                nbNeighbours++;
            }
        }

        return nbNeighbours;
    }

    @Override
    public boolean equals(Object newCoordinate) {
        if (newCoordinate instanceof Coordinate) {
            Coordinate toTest = (Coordinate) newCoordinate;
            if ((this.x == toTest.getX()) && (this.y == toTest.getY())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "x = " + this.x + ", y = " + this.y;
    }
}
