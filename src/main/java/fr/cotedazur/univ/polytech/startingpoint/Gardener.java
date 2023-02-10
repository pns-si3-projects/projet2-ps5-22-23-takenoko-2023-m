package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Gardener {
    private Coordinate coordinate;
    private final Board board;
    private Tile tile;

    /**
     * The constructor of the class
     * @param board The board of the gardener
     */
    public Gardener(Board board){
        coordinate = new Coordinate(0,0);
        this.board = board;
        this.tile = board.getTile(new Coordinate(0,0));
    }

    /**
     * The method to move the gardener of a specific coordinate
     * @param coordinate The specific coordinate
     * @return The string of the movement
     */
    public String moveOn(Coordinate coordinate){
        this.coordinate = coordinate;
        this.tile = board.getTile(coordinate);

        return spreadGrowing(coordinate);
    }

    /**
     * The method will grow bamboo on the tile
     * @param coordinate The coordinate of the tile
     * @return The string of the movement
     */
    private String spreadGrowing(Coordinate coordinate) {;
        ArrayList<Tile> tilesGrown = new ArrayList<>();
        tilesGrown.add(board.getTile(coordinate));
        board.getTile(coordinate).grow();
        int sizeGrown = 1;
        String res = "";
        for(int i = 0; i<sizeGrown;i++){
            for(Coordinate c : tilesGrown.get(i).getNeighbourCoordinates()){
                if(board.isInBoard(c.getX(),c.getY())&&board.getTile(c).getTypeOfTile().equals(tile.getTypeOfTile())&& !(tilesGrown.contains(board.getTile(c)))){
                    tilesGrown.add(board.getTile(c));
                    board.getTile(c).grow();
                    sizeGrown++;
                    res += "La case "+board.getTile(c).getCoordinate()+" a pousse et est maintenant a "+ board.getTile(c).getBamboo() +" bambou(s)\n";
                }
            }
        }
        return res;
    }

    /**
     * A getter of the coordinate of the gardener
     * @return The coordinate of the gardener
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * A getter of the tile of the gardener
     * @return the tile of the gardener
     */
    public Tile getTile(){
        return this.board.getTile(this.getCoordinate());
    }
}
