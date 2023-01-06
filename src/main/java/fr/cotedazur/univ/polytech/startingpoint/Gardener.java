package fr.cotedazur.univ.polytech.startingpoint;

public class Gardener {
    private Coordinate coordinate;
    private final Board board;
    public Gardener(Board board){
        coordinate = new Coordinate(0,0);
        this.board = board;
    }
    //move the entity gardener on a designed tile, growing 1 bamboo of this tile
    public int moveOn(Coordinate coordinate){
        this.coordinate = coordinate;
        Tile tileToGrow = board.getTile(coordinate);
        return tileToGrow.grow(1);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
