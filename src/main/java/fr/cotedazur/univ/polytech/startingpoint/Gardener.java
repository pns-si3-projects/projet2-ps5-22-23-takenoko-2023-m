package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Gardener {
    private Coordinate coordinate;
    private final Board board;
    private Tile tile;
    public Gardener(Board board){
        coordinate = new Coordinate(0,0);
        this.board = board;
        this.tile = board.getTile(new Coordinate(0,0));
    }
    //move the entity gardener on a designed tile, growing 1 bamboo of this tile
    public String moveOn(Coordinate coordinate){
        this.coordinate = coordinate;
        this.tile = board.getTile(coordinate);

        return spreadGrowing(coordinate);
    }

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

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
