package fr.cotedazur.univ.polytech.startingpoint;

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

    @Override
    public boolean equals(Object newCoordinate) {
        Coordinate toTest = (Coordinate) newCoordinate;
        if ((this.x == toTest.getX()) & (this.y == toTest.getY())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "x = " + this.x + ", y = " + this.y;
    }
}