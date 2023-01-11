package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class PatternDetector {
    final Board board;
    final ArrayList<PatternBoard> patternBoardList = new ArrayList<>();

    public PatternDetector(Board board) {
        this.board = board;
    }
    public void detectPatternNear(Coordinate coordinate){
        ArrayList<Tile> tileOfSameType = this.detectTileOfSameTypeNear(coordinate);
        for(Tile tile : tileOfSameType){
            //detection of a pattern of type LINE
            Coordinate tileCoord = tile.getCoordinate();
            int possibleTileX = tileCoord.getX()+tileCoord.getX()-coordinate.getX();
            int possibleTileY = tileCoord.getY()+tileCoord.getY()-coordinate.getY();
            //check if a tile exist on this position and if it's the same type
            if(board.isInBoard(possibleTileX,possibleTileY)&&board.getTile(new Coordinate(possibleTileX,possibleTileY)).getTypeOfTile().equals(tile.getTypeOfTile())){
                patternBoardList.add(new PatternBoard(TypeOfPattern.LINE,tile.getTypeOfTile(),new Coordinate(possibleTileX,possibleTileY)));
            }

        }
    }

    private ArrayList<Tile> detectTileOfSameTypeNear(Coordinate coordinate) {
        ArrayList<Tile> tileOfSameType = new ArrayList<>();
        ArrayList<Coordinate> neighbourCoordinates = board.getTile(coordinate).getNeighbourCoordinates();
        neighbourCoordinates.forEach(c-> {
            if(board.isInBoard(c.getX(),c.getY()) && board.getTile(c).getTypeOfTile().equals(board.getTile(coordinate).getTypeOfTile())){
                tileOfSameType.add(board.getTile(c));
            }
        });
        return tileOfSameType;
    }
}
