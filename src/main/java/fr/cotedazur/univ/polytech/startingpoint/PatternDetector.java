package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class PatternDetector {
    private final Board board;
    final List<Pattern> patternBoardList = new ArrayList<>();

    public PatternDetector(Board board) {
        this.board = board;
    }

    /**
     * This method is used to detect all the patterns on the board of which a new tile has been added
     * @param coordinate the coordinate of the new tile
     */
    public void detectPatternNear(Coordinate coordinate){
        System.out.println(board.getBoardTiles());
        System.out.println(coordinate);
        if(!board.getTile(coordinate).isIrrigated()){
            return;
        }
        List<Tile> tileOfSameType = this.detectTileOfSameTypeNear(coordinate);
        tileOfSameType = this.filterTileNotIrrigate(tileOfSameType);
        for(Tile tile : tileOfSameType){
            boolean isDetectedAsLineOrTriangle;
            //detection of a pattern of type LINE
            isDetectedAsLineOrTriangle = this.detectIfLine(tile,coordinate);
            if(tileOfSameType.size()>1) {
                if(isDetectedAsLineOrTriangle){
                    this.detectIfTriangle(tile, coordinate, tileOfSameType);
                }
                else{
                    isDetectedAsLineOrTriangle = this.detectIfTriangle(tile, coordinate, tileOfSameType);
                }
                //detection of a pattern of tye BOOMRANG when the coordinate is at the center of the pattern
                if (!isDetectedAsLineOrTriangle) {
                    addToPatternList(new Pattern(TypeOfPattern.BOOMRANG, tile.getTypeOfTile()));
                }
            }
            if(!isDetectedAsLineOrTriangle){
                List<Tile> tileOfSameType2 = this.detectTileOfSameTypeNear(tile.getCoordinate());
                tileOfSameType2.remove(board.getTile(coordinate));
                tileOfSameType2 = this.filterTileNotIrrigate(tileOfSameType2);
                if(!tileOfSameType2.isEmpty() ){
                    addToPatternList(new Pattern(TypeOfPattern.BOOMRANG,tile.getTypeOfTile()));
                }
            }
        }



        }

    private List<Tile> filterTileNotIrrigate(List<Tile> tileOfSameType) {
        List<Tile> tileOfSameTypeIrrigate = new ArrayList<>();
        for(Tile tile : tileOfSameType){
            if(tile.isIrrigated()){
                tileOfSameTypeIrrigate.add(tile);
            }
        }
        return tileOfSameTypeIrrigate;
    }

    private boolean detectIfTriangle(Tile tile, Coordinate coordinate, List<Tile> tileOfSameType) {
        tileOfSameType = this.filterTileNotIrrigate(tileOfSameType);
        for(Tile tile2 : tileOfSameType){
            if(!tile.equals(tile2)&&tile.isNeighbour(board.getTile(coordinate))&&tile2.isNeighbour(board.getTile(coordinate))&&tile.isNeighbour(tile2)){
                addToPatternList(new Pattern(TypeOfPattern.TRIANGLE,tile.getTypeOfTile()));
                return true;
            }
        }
        return false;
    }

    private boolean detectIfLine(Tile tile, Coordinate coordinate) {
        Coordinate tileCoordinate = tile.getCoordinate();
        //possible position of a tile to create a pattern line
        int possibleTileX = tileCoordinate.getX()+tileCoordinate.getX()-coordinate.getX();
        int possibleTileY = tileCoordinate.getY()+tileCoordinate.getY()-coordinate.getY();
        int possibleTileX2 = coordinate.getX()+coordinate.getX()-tileCoordinate.getX();
        int possibleTileY2 = coordinate.getY()+coordinate.getY()-tileCoordinate.getY();
        //check if a tile exist on this position and if it's the same type
        if(board.isInBoard(possibleTileX,possibleTileY)&&board.getTile(new Coordinate(possibleTileX,possibleTileY)).getTypeOfTile().equals(tile.getTypeOfTile())&& board.getTile(new Coordinate(possibleTileX,possibleTileY)).isIrrigated()){
            addToPatternList(new Pattern(TypeOfPattern.LINE,tile.getTypeOfTile()));
            return true;
        }
        if(board.isInBoard(possibleTileX2,possibleTileY2)&&board.getTile(new Coordinate(possibleTileX2,possibleTileY2)).getTypeOfTile().equals(tile.getTypeOfTile())&&board.getTile(new Coordinate(possibleTileX2,possibleTileY2)).isIrrigated()){
            addToPatternList(new Pattern(TypeOfPattern.LINE,tile.getTypeOfTile()));
            return true;
        }
        return false;
    }

    private void addToPatternList(Pattern pattern) {
        if(!patternBoardList.contains(pattern)){
            patternBoardList.add(pattern);
        }
    }

    public List<Pattern> getPatternBoardList() {
        return patternBoardList;
    }

    /**
     * This method is used to detect all the tiles of the same type near a coordinate
     * @param coordinate the coordinate of the tile to check
     * @return an ArrayList of tiles of the same type
     */
    private List<Tile> detectTileOfSameTypeNear(Coordinate coordinate) {
        List<Tile> tileOfSameType = new ArrayList<>();
        List<Coordinate> neighbourCoordinates = board.getTile(coordinate).getNeighbourCoordinates();
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
            List<Coordinate> tilesOfSameTypeNearCoordinate = new ArrayList<>();
            detectTileOfSameTypeNear(firstTileCoordinate).forEach(t->tilesOfSameTypeNearCoordinate.add(t.getCoordinate()));
            Coordinate possibleCoordinate = bestCoordinateForLineNear(firstTileCoordinate,tilesOfSameTypeNearCoordinate);
            if(possibleCoordinate!=null){
                return possibleCoordinate;
            }
         if(bestCoordinate == null){
                List<Coordinate> availableCoordinate = board.getAvailableCoordinateNear(firstTileCoordinate);
                bestCoordinate = bestCoordinateForLineNear(firstTileCoordinate,availableCoordinate);
            }
        }
        return bestCoordinate==null?board.scanAvailableTilePosition().get(0):bestCoordinate;
    }

    private Coordinate bestCoordinateForLineNear(Coordinate firstTileCoordinate, List<Coordinate> tilesOfSameTypeNearCoordinate) {
        if(!tilesOfSameTypeNearCoordinate.isEmpty()){
            for(Coordinate secondTileCoordinate : tilesOfSameTypeNearCoordinate){
                int possibleTileX = secondTileCoordinate.getX()+secondTileCoordinate.getX()-firstTileCoordinate.getX();
                int possibleTileY = secondTileCoordinate.getY()+secondTileCoordinate.getY()-firstTileCoordinate.getY();
                if(!board.isInBoard(possibleTileX,possibleTileY)){
                    if(board.isInBoard(secondTileCoordinate.getX(), secondTileCoordinate.getY())){
                        return new Coordinate(possibleTileX,possibleTileY);
                    }
                    return secondTileCoordinate;
                }
            }
        }
        return null;
    }

    /**
     * find the best coordinate to place a tile to complete a pattern of type TRIANGLE
     * @param objectivePlot the objective we need to complete
     * @return the best coordinate to place a tile
     */
    public Coordinate bestCoordinateForTriangle(ObjectivePlot objectivePlot) {
        Coordinate bestCoordinate = null;
        for(Tile tile : this.getBoardTileOfType(objectivePlot.getColors())){
            Coordinate firstTileCoordinate = tile.getCoordinate();
            List<Coordinate> tilesOfSameTypeNearCoordinate = new ArrayList<>();
            detectTileOfSameTypeNear(firstTileCoordinate).forEach(t->tilesOfSameTypeNearCoordinate.add(t.getCoordinate()));
            Coordinate possibleCoordinate = bestCoordinateForTriangleNear(tile,tilesOfSameTypeNearCoordinate);
            if(possibleCoordinate!=null){
                return possibleCoordinate;
            }
            if(bestCoordinate == null){
                List<Coordinate> availableCoordinate = board.getAvailableCoordinateNear(firstTileCoordinate);
                bestCoordinate = bestCoordinateForTriangleNear(tile,availableCoordinate);
            }

        }
        return bestCoordinate==null?board.scanAvailableTilePosition().get(0):bestCoordinate;
    }
    private Coordinate bestCoordinateForTriangleNear(Tile tile, List<Coordinate> availableCoordinate) {
        if(!availableCoordinate.isEmpty()){
            for(Coordinate coordinate : availableCoordinate){
                for(Coordinate coordinateTogether : tile.getNeighbourCoordinateTogetherWith(new Tile(coordinate))){
                    if(!board.isInBoard(coordinateTogether.getX(),coordinateTogether.getY())){
                        return coordinateTogether;
                    }
                }
            }
        }
        return null;
    }
    public Coordinate bestCoordinateForBoomrang(ObjectivePlot objectivePlot){
        Coordinate bestCoordinate = null;
        for(Tile tile : this.getBoardTileOfType(objectivePlot.getColors())){
            Coordinate firstTileCoordinate = tile.getCoordinate();
            List<Coordinate> tilesOfSameTypeNearCoordinate = new ArrayList<>();
            detectTileOfSameTypeNear(firstTileCoordinate).forEach(t->tilesOfSameTypeNearCoordinate.add(t.getCoordinate()));
            if(!tilesOfSameTypeNearCoordinate.isEmpty()){
                for(Coordinate coordinate : tilesOfSameTypeNearCoordinate){
                    if(board.isInBoard(coordinate.getX(),coordinate.getY())) {
                        List<Coordinate> possibleCoordinates = board.getAvailableCoordinateNear(coordinate);
                        //remove coordinate forming a triangle
                        possibleCoordinates.removeAll(tile.getNeighbourCoordinateTogetherWith(new Tile(coordinate)));

                        possibleCoordinates.remove(bestCoordinateForLineNear(firstTileCoordinate,List.of(coordinate)));
                        possibleCoordinates.remove(bestCoordinateForLineNear(firstTileCoordinate,List.of(coordinate)));
                        //remove coordinate forming a line
                        for (Coordinate coordinateTogether : possibleCoordinates) {
                            if (!board.isInBoard(coordinateTogether.getX(), coordinateTogether.getY())) {
                                return coordinateTogether;
                            }
                        }
                    }
                }
            }
            if(bestCoordinate == null){
                List<Coordinate> availableCoordinate = board.getAvailableCoordinateNear(firstTileCoordinate);
                bestCoordinate = bestCoordinateForBoomrangNear(tile,availableCoordinate);
            }

        }
        return bestCoordinate==null?board.scanAvailableTilePosition().get(0):bestCoordinate;
    }

    private Coordinate bestCoordinateForBoomrangNear(Tile tile, List<Coordinate> availableCoordinate) {
        if(!availableCoordinate.isEmpty()){
            for(Coordinate coordinate : availableCoordinate){
                    List<Coordinate> possibleCoordinates = new Tile(coordinate).getNeighbourCoordinates();
                    possibleCoordinates.remove(tile.getCoordinate());
                    possibleCoordinates.remove(bestCoordinateForLineNear(coordinate, possibleCoordinates));
                    possibleCoordinates.remove(bestCoordinateForLineNear(coordinate, possibleCoordinates));
                    possibleCoordinates.remove(bestCoordinateForTriangleNear(tile, possibleCoordinates));
                    possibleCoordinates.remove(bestCoordinateForTriangleNear(tile, possibleCoordinates));
                    for (Coordinate coordinateTogether : possibleCoordinates) {
                        if (!board.isInBoard(coordinateTogether.getX(), coordinateTogether.getY())) {
                            return coordinate;
                        }
                    }
            }
        }
        return null;
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
