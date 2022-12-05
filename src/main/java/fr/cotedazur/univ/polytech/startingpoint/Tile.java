package fr.cotedazur.univ.polytech.startingpoint;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

public class Tile {
    private final int coordinnateX;
    private final int coordinnateY;
    public Tile(int x, int y){
        coordinnateX = x;
        coordinnateY = y;
    }

    public int getCoordinnateX() {
        return coordinnateX;
    }

    public int getCoordinnateY(){
        return coordinnateY;
    }

    //tests to see if the tile to test is neighbour to this tile
    //check coordinate system at : https://www.redblobgames.com/grids/hexagons/#neighbors-axial
    public boolean isNeighbour (Tile tileToTest) {
        ArrayList<Tile> neighboursToTest = this.getNeighbourCoordinates();
        for (int i = 0; i < 6; i++) {
            boolean sameX = tileToTest.getCoordinnateX() == neighboursToTest.get(i).getCoordinnateX();
            boolean sameY = tileToTest.getCoordinnateY() == neighboursToTest.get(i).getCoordinnateY();
            if (sameX && sameY) {
                return true;
            }
        }
        return false;
    }

    //returns the neighbour tiles of this tile
    public ArrayList<Tile> getNeighbourCoordinates () {
        ArrayList<Tile> neighbourCoordinates = new ArrayList<>();   //this ArrayList gets all the neighbour position of this tile
        //attribution of neighbourCoordinates
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != j) {   //gets rid of (-1, -1), (+0, +0), (+1, +1)
                    int xToTest = coordinnateX + i;
                    int yToTest = coordinnateY + j;
                    Tile potentialNeighbour = new Tile(xToTest, yToTest);
                    neighbourCoordinates.add(potentialNeighbour);
                }
            }
        }

        /*
        //check the result
        System.out.println(neighbourCoordinates.size());
        for (int i = 0; i < 6; i++) {
            System.out.println(neighbourCoordinates.get(i));
        }*/

        return neighbourCoordinates;
    }


    @Override
    public String toString() {
        String str = "Tile at x = " + coordinnateX + ", y = " + coordinnateY;
        return str;
    }
}
