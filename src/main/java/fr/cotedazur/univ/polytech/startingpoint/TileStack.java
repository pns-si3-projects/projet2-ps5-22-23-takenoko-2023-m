package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TileStack extends Stack<Tile>{
    /**
     * A constructor with one parameter
     * @param list The list of the stack
     */
    public TileStack(List<Tile> list) {
        super(list);
    }

    /**
     * A constructor with no parameter
     */
    public TileStack() {

        super();
        this.generate();
    }


    /**
     * A method to pick 3 tiles in the stack
     * @return
     */
    public List<Tile> pickThreeTiles(){
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            Tile tile = randomPick();
            tiles.add(tile);
            list.remove(tile);
        }

        return tiles;
    }

    /**
     * A method to put a tile in the stack
     * @param tile The specific tile
     */
    public void addTile(Tile tile){
        list.add(tile);
    }

    /**
     * A setter of the stack
     * @param list The new stack
     */
    public void setStack(List<Tile> list){
        this.list = list;
    }


    /**
     * A method to generate the tileStack
     */
    @Override
    public void generate() {
        for(int i = 0; i < 6; i++){
            this.putBelow(new Tile(TypeOfTile.YELLOW));
        }
        this.putBelow(new Tile(TypeOfTile.YELLOW,TypeOfArrangement.FERTILIZER));
        this.putBelow(new Tile(TypeOfTile.YELLOW,TypeOfArrangement.BASIN));
        this.putBelow(new Tile(TypeOfTile.YELLOW,TypeOfArrangement.ENCLOSURE));
        for(int i = 0; i < 8; i++){
            this.putBelow(new Tile(TypeOfTile.GREEN));
        }
        this.putBelow(new Tile(TypeOfTile.GREEN,TypeOfArrangement.FERTILIZER));
        this.putBelow(new Tile(TypeOfTile.GREEN,TypeOfArrangement.BASIN));
        this.putBelow(new Tile(TypeOfTile.GREEN,TypeOfArrangement.ENCLOSURE));
        for(int i = 0; i < 4; i++){
            this.putBelow(new Tile(TypeOfTile.RED));
        }
        this.putBelow(new Tile(TypeOfTile.RED,TypeOfArrangement.FERTILIZER));
        this.putBelow(new Tile(TypeOfTile.RED,TypeOfArrangement.BASIN));
        this.putBelow(new Tile(TypeOfTile.RED,TypeOfArrangement.ENCLOSURE));
    }

    /**
     * A method to return the size of the stack
     * @return The size of the stack
     */
    public int sizeTileStack(){
        return this.list.size();
    }
}
