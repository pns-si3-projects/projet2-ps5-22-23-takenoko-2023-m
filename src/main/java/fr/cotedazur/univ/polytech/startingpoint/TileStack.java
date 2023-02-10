package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TileStack extends Stack<Tile>{
    public TileStack(List<Tile> list) {
        super(list);
    }

    public TileStack() {

        super();
        this.generate();
    }




    public List<Tile> pickThreeTiles(){
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < 3; i++){
            Tile tile = randomPick();
            tiles.add(tile);
            list.remove(tile);
        }

        return tiles;
    }

    public void addTile(Tile tile){
        list.add(tile);
    }

    public void setStack(List<Tile> list){
        this.list = list;
    }



    @Override
    public void generate() {
        for(int i = 0; i < 6; i++){
            this.putBelow(new Tile(null, TypeOfTile.YELLOW, TypeOfArrangement.NONE));
        }
        this.putBelow(new Tile(null, TypeOfTile.YELLOW,TypeOfArrangement.FERTILIZER));
        this.putBelow(new Tile(null, TypeOfTile.YELLOW,TypeOfArrangement.BASIN));
        this.putBelow(new Tile(null, TypeOfTile.YELLOW,TypeOfArrangement.ENCLOSURE));
        for(int i = 0; i < 8; i++){
            this.putBelow(new Tile(null, TypeOfTile.GREEN, TypeOfArrangement.NONE));
        }
        this.putBelow(new Tile(null, TypeOfTile.GREEN,TypeOfArrangement.FERTILIZER));
        this.putBelow(new Tile(null, TypeOfTile.GREEN,TypeOfArrangement.BASIN));
        this.putBelow(new Tile(null, TypeOfTile.GREEN,TypeOfArrangement.ENCLOSURE));
        for(int i = 0; i < 4; i++){
            this.putBelow(new Tile(null, TypeOfTile.RED, TypeOfArrangement.NONE));
        }
        this.putBelow(new Tile(null, TypeOfTile.RED,TypeOfArrangement.FERTILIZER));
        this.putBelow(new Tile(null, TypeOfTile.RED,TypeOfArrangement.BASIN));
        this.putBelow(new Tile(null, TypeOfTile.RED,TypeOfArrangement.ENCLOSURE));
    }

    public int sizeTileStack(){
        return this.list.size();
    }
}
