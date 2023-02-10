package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;

public class Panda {
    private Coordinate coordinate;
    private Board board;

    /**
     * The constructor of the class
     * @param board
     */
    public Panda(Board board){
        coordinate = new Coordinate(0,0);
        this.board = board;
    }

    /**
     * A method to moove the panda on a specific coordinate
     * @param coordinate The coordinate to moove
     * @param p The player
     */
    public void moveOn(Coordinate coordinate, Bot p){
        this.coordinate = coordinate;
        Tile tileToGrow = board.getTile(coordinate);
        if(tileToGrow.getBamboo() > 0){
            tileToGrow.eatBamboo();
            p.upNbBamboo(tileToGrow.getTypeOfTile());
        }
    }

    /**
     * A getter of the tile of the panda
     * @return the tile of the panda
     */
    public Tile getTile(){
        return this.board.getTile(coordinate);
    }
}
