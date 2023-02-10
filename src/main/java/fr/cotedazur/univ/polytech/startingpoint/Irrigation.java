package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Irrigation {


    private final Tile referencedTile1;   //must be a reference to a tile placed in the board
    private final Tile referencedTile2;   //must be a reference to a tile placed in the board
    private Coordinate coordinate1;
    private Coordinate coordinate2;

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
        this.fixCoordinateOrder();


    }

    public Irrigation (Coordinate coordinate1, Coordinate coordinate2) throws RuntimeException {
        List<Coordinate> coordinate1Neighbours = coordinate1.getNeighbourCoordinates();
        if (!coordinate1Neighbours.contains(coordinate2)) {
            throw new RuntimeException("Tiles are not neighbours ( " + coordinate1 + "\t\t" + coordinate2 + " )");
        }

        this.coordinate1 = coordinate1;
        this.coordinate2 = coordinate2;

        this.referencedTile1 = null;
        this.referencedTile2 = null;

        this.irrigationType = this.detectTypeOfIrrigation();
        this.fixCoordinateOrder();
    }

    private void fixCoordinateOrder() {
        switch (irrigationType) {
            case vertical -> {
                if (coordinate1.getX() > coordinate2.getX()) {
                    Coordinate tmp = coordinate1;
                    this.coordinate1 = coordinate2;
                    this.coordinate2 = tmp;
                }
            }
            case fSlash -> {
                if (coordinate1.getY() > coordinate2.getY()) {
                    Coordinate tmp = coordinate1;
                    this.coordinate1 = coordinate2;
                    this.coordinate2 = tmp;
                }
            }
            case bSlash -> {
                if (coordinate1.getX() > coordinate2.getX()) {
                    Coordinate tmp = coordinate1;
                    this.coordinate1 = coordinate2;
                    this.coordinate2 = tmp;
                }
            }




        }
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


    public List<Irrigation> getNeighbourIrrigations() {
        List<Irrigation> neighbours = new ArrayList<>();
        switch (irrigationType) {
            case vertical -> {
                Irrigation topLeftNeighbour = new Irrigation(coordinate1, new Coordinate(coordinate2.getX(), coordinate2.getY()-1));
                Irrigation topRightNeighbour = new Irrigation(coordinate2, new Coordinate(coordinate2.getX(), coordinate2.getY()-1));
                Irrigation botLeftNeighbour = new Irrigation(coordinate1, new Coordinate(coordinate1.getX(), coordinate1.getY()+1));
                Irrigation botRightNeighbour = new Irrigation(coordinate2, new Coordinate(coordinate1.getX(), coordinate1.getY()+1));
                neighbours.add(topLeftNeighbour);
                neighbours.add(topRightNeighbour);
                neighbours.add(botLeftNeighbour);
                neighbours.add(botRightNeighbour);
            }
            case fSlash -> {
                Irrigation leftTopNeighbour = new Irrigation(coordinate1, new Coordinate(coordinate2.getX()-1, coordinate2.getY()));
                Irrigation leftBotNeighbour = new Irrigation(coordinate2, new Coordinate(coordinate2.getX()-1, coordinate2.getY()));
                Irrigation rightTopNeighbour = new Irrigation(coordinate1, new Coordinate(coordinate1.getX()+1, coordinate1.getY()));
                Irrigation rightBotNeighbour = new Irrigation(coordinate2, new Coordinate(coordinate1.getX()+1, coordinate1.getY()));
                neighbours.add(leftTopNeighbour);
                neighbours.add(leftBotNeighbour);
                neighbours.add(rightTopNeighbour);
                neighbours.add(rightBotNeighbour);
            }
            case bSlash -> {
                Irrigation leftTopIrrigation = new Irrigation(coordinate2, new Coordinate(coordinate1.getX(), coordinate2.getY()));
                Irrigation leftBotIrrigation = new Irrigation(coordinate1, new Coordinate(coordinate1.getX(), coordinate2.getY()));
                Irrigation rightTopIrrigation = new Irrigation(coordinate2, new Coordinate(coordinate2.getX(), coordinate1.getY()));
                Irrigation rightBotIrrigation = new Irrigation(coordinate1, new Coordinate(coordinate2.getX(), coordinate1.getY()));
                neighbours.add(leftTopIrrigation);
                neighbours.add(leftBotIrrigation);
                neighbours.add(rightTopIrrigation);
                neighbours.add(rightBotIrrigation);
            }
        }
        return neighbours;
    }

    public boolean isNeighbour (Irrigation potentialNeighbour) {
        List<Irrigation> thisNeighbours = this.getNeighbourIrrigations();
        return thisNeighbours.contains(potentialNeighbour);
    }

    @Override
    public String toString() {
        return "Irrigation between : " + coordinate1.toString() + "\t\t" + coordinate2.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Irrigation i) {
            if ((this.coordinate1.equals(i.getCoordinates().get(0))) && (this.coordinate2.equals(i.getCoordinates().get(1)))) {
                return true;
            }
            if ((this.coordinate2.equals(i.getCoordinates().get(0))) && (this.coordinate1.equals(i.getCoordinates().get(1)))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate1, coordinate2);
    }
}
