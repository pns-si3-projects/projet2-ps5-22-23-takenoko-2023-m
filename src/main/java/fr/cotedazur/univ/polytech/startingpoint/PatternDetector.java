package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class PatternDetector {
    private final Board board;
    final ArrayList<Pattern> patternBoardList = new ArrayList<>();

    /**
     * A constructor of the class
     * @param board The board
     */
    public PatternDetector(Board board) {
        this.board = board;
    }

    /**
     * This method is used to detect all the patterns on the board of which a new tile has been added
     * @param coordinate the coordinate of the new tile
     */
    public void detectPatternNear(Coordinate coordinate){
        List<Tile> tileOfSameType = this.detectTileOfSameTypeNear(coordinate);
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
                if(!tileOfSameType2.isEmpty() ){
                    addToPatternList(new Pattern(TypeOfPattern.BOOMRANG,tile.getTypeOfTile()));
                }
            }
        }



        }

    /**
     * Detect if there is a triangle
      * @param tile The specific tile
     * @param coordinate The specific coordinte
     * @param tileOfSameType A list of tile of the same type
     * @return True or false
     */
    private boolean detectIfTriangle(Tile tile, Coordinate coordinate, List<Tile> tileOfSameType) {
        for(Tile tile2 : tileOfSameType){
            if(!tile.equals(tile2)&&tile.isNeighbour(board.getTile(coordinate))&&tile2.isNeighbour(board.getTile(coordinate))&&tile.isNeighbour(tile2)){
                addToPatternList(new Pattern(TypeOfPattern.TRIANGLE,tile.getTypeOfTile()));
                return true;
            }
        }
        return false;
    }

    /**
     * Detect if there is a line
     * @param tile The specific tile
     * @param coordinate The specific coordinate
     * @return True if it detect a line, false if it don't detect
     */
    private boolean detectIfLine(Tile tile, Coordinate coordinate) {
        Coordinate tileCoordinate = tile.getCoordinate();
        //possible position of a tile to create a pattern line
        int possibleTileX = tileCoordinate.getX()+tileCoordinate.getX()-coordinate.getX();
        int possibleTileY = tileCoordinate.getY()+tileCoordinate.getY()-coordinate.getY();
        int possibleTileX2 = coordinate.getX()+coordinate.getX()-tileCoordinate.getX();
        int possibleTileY2 = coordinate.getY()+coordinate.getY()-tileCoordinate.getY();
        //check if a tile exist on this position and if it's the same type
        if(board.isInBoard(possibleTileX,possibleTileY)&&board.getTile(new Coordinate(possibleTileX,possibleTileY)).getTypeOfTile().equals(tile.getTypeOfTile())){
            addToPatternList(new Pattern(TypeOfPattern.LINE,tile.getTypeOfTile()));
            return true;
        }
        if(board.isInBoard(possibleTileX2,possibleTileY2)&&board.getTile(new Coordinate(possibleTileX2,possibleTileY2)).getTypeOfTile().equals(tile.getTypeOfTile())){
            addToPatternList(new Pattern(TypeOfPattern.LINE,tile.getTypeOfTile()));
            return true;
        }
        return false;
    }

    /**
     * A method to add a pattern to the list
     * @param pattern The pattern to add
     */
    private void addToPatternList(Pattern pattern) {
        if(!patternBoardList.contains(pattern)){
            patternBoardList.add(pattern);
        }
    }

    /**
     * A method to get the pattern board list
     * @return the pattern board list
     */
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

    /**
     * A method to find the best coordinate for a line
     * @param firstTileCoordinate A coordinate
     * @param tilesOfSameTypeNearCoordinate A list of the tile of the same type near the first param
     * @return The best coordinate
     */
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

    /**
     * Find the best coordinate for the triangle
     * @param tile The tile to start
     * @param availableCoordinate The available coordinates
     * @return The best coordinate
     */
    private Coordinate bestCoordinateForTriangleNear(Tile tile, List<Coordinate> availableCoordinate) {
        if(!availableCoordinate.isEmpty()){
            for(Coordinate coordinate : availableCoordinate){
                for(Coordinate coordinateTogether : tile.getNeighbourCoordinateTogetherWith(new Tile(coordinate, TypeOfTile.POND, TypeOfArrangement.NONE))){
                    if(!board.isInBoard(coordinateTogether.getX(),coordinateTogether.getY())){
                        return coordinateTogether;
                    }
                }
            }
        }
        return null;
    }

    /**
     * A method to find the best coordinate for the boomerang
     * @param objectivePlot The objective plot
     * @return The best coordinate
     */
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
                        possibleCoordinates.removeAll(tile.getNeighbourCoordinateTogetherWith(new Tile(coordinate, TypeOfTile.POND, TypeOfArrangement.NONE)));

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

    /**
     * Method to find the best coordinate for the boomerang
     * @param tile The tile to start
     * @param availableCoordinate The availables coordinates
     * @return the best coordinate
     */
    private Coordinate bestCoordinateForBoomrangNear(Tile tile, List<Coordinate> availableCoordinate) {
        if(!availableCoordinate.isEmpty()){
            for(Coordinate coordinate : availableCoordinate){
                    List<Coordinate> possibleCoordinates = new Tile(coordinate, TypeOfTile.POND, TypeOfArrangement.NONE).getNeighbourCoordinates();
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
