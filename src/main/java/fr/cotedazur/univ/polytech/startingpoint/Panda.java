package fr.cotedazur.univ.polytech.startingpoint;

public class Panda {
    private Coordinate coordinate;
    private Board board;

    public Panda(Board board){
        coordinate = new Coordinate(0,0);
        this.board = board;
    }
    //move the entity gardener on a designed tile, growing 1 bamboo of this tile
    public void moveOn(Coordinate coordinate, Player p){
        this.coordinate = coordinate;
        Tile tileToGrow = board.getTile(coordinate);
        if(tileToGrow.getBamboo() > 0){
            tileToGrow.eatBamboo();
            p.upNbBamboo();
        }
    }
}
