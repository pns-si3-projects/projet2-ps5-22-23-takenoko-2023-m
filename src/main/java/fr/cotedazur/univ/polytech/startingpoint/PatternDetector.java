package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class PatternDetector {
    private final Board board;
    final ArrayList<Pattern> patternBoardList = new ArrayList<>();

    public PatternDetector(Board board) {
        this.board = board;
    }

    /**
     * This method is used to detect all the patterns on the board of which a new tile has been added
     * @param coordinate the coordinate of the new tile
     */
    public void detectPatternNear(Coordinate coordinate){
        ArrayList<Tile> tileOfSameType = this.detectTileOfSameTypeNear(coordinate);
        for(Tile tile : tileOfSameType){
            //detection of a pattern of type LINE
            //TODO : detect if the LINE pattern even if the tile is place at the center of the line
            Coordinate tileCoord = tile.getCoordinate();
            int possibleTileX = tileCoord.getX()+tileCoord.getX()-coordinate.getX();
            int possibleTileY = tileCoord.getY()+tileCoord.getY()-coordinate.getY();
            //check if a tile exist on this position and if it's the same type
            if(board.isInBoard(possibleTileX,possibleTileY)&&board.getTile(new Coordinate(possibleTileX,possibleTileY)).getTypeOfTile().equals(tile.getTypeOfTile())){
                patternBoardList.add(new Pattern(TypeOfPattern.LINE,tile.getTypeOfTile()));
            }

        }
    }

    public ArrayList<Pattern> getPatternBoardList() {
        return patternBoardList;
    }

    /**
     * This method is used to detect all the tiles of the same type near a coordinate
     * @param coordinate the coordinate of the tile to check
     * @return an ArrayList of tiles of the same type
     */
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

    /**
     * find the best coordinate to place a tile to complete a pattern of type LINE
     * @param objectivePlot the objective we need to complete
     * @return the best coordinate to place a tile
     */
    public Coordinate bestCoordinateForLine(ObjectivePlot objectivePlot) {
        Coordinate bestCoordinate = null;
        for(Tile tile : this.getBoardTileOfType(objectivePlot.getColors())){
            Coordinate firstTileCoordinate = tile.getCoordinate();
            List<Tile> tilesOfSameTypeNear = detectTileOfSameTypeNear(firstTileCoordinate);
            if(!tilesOfSameTypeNear.isEmpty()){
                for(Tile tileOfSameTypeNear : tilesOfSameTypeNear){
                    Coordinate secondTileCoordinate = tileOfSameTypeNear.getCoordinate();
                    int possibleTileX = secondTileCoordinate.getX()+secondTileCoordinate.getX()-firstTileCoordinate.getX();
                    int possibleTileY = secondTileCoordinate.getY()+secondTileCoordinate.getY()-firstTileCoordinate.getY();
                    if(!board.isInBoard(possibleTileX,possibleTileY)){
                        return new Coordinate(possibleTileX,possibleTileY);
                    }
                }
            }
            if(bestCoordinate == null){
                List<Coordinate> availableCoordinate = board.getAvailableCoordinateNear(firstTileCoordinate);
                for(Coordinate coordinate : availableCoordinate){
                    int possibleTileX = coordinate.getX()+coordinate.getX()-firstTileCoordinate.getX();
                    int possibleTileY = coordinate.getY()+coordinate.getY()-firstTileCoordinate.getY();
                    if(!board.isInBoard(possibleTileX,possibleTileY)){
                        bestCoordinate = coordinate;
                    }
                }
            }
        }
        if(bestCoordinate == null)
            bestCoordinate = board.scanAvailableTilePosition().get(0);
        return bestCoordinate;
    }

    /**
     * Find in the board all the tiles of a specific type
     * @param colors the types of tile we want to find
     * @return an ArrayList of tiles of the specific type
     */
    private List<Tile> getBoardTileOfType(List<TypeOfTile> colors) {
        List<Tile> boardTilesOfType = new ArrayList<>();
        for(Tile tile : board.getBoardTiles()){
            if(colors.contains(tile.getTypeOfTile())){
                boardTilesOfType.add(tile);
            }
        }
        return boardTilesOfType;
    }
}
