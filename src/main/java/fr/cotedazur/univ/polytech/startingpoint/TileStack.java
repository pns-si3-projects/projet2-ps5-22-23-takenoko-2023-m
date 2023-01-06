package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;
import java.util.List;

public class TileStack extends Stack<Tile>{
    public TileStack(List<Tile> list) {
        super(list);
    }

    public TileStack() {
        super();
    }

    @Override
    public void putBelow(Tile o) {
        super.putBelow(o);
    }

    @Override
    public Tile pick(Tile o) {
        return super.pick(o);
    }

    @Override
    public Tile randomPick() {
        return super.randomPick();
    }

    public List<Tile> pickThreeTiles(){
        List<Tile> tiles = new ArrayList<Tile>();
        for (int i = 0; i < 3; i++){
            tiles.add(randomPick());
        }
        return tiles;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public List<Tile> getStack() {
        return super.getStack();
    }

    @Override
    public void generate() {
        for(int i = 0; i < 9; i++){
            this.putBelow(new Tile(TypeOfTile.YELLOW));
        }
        for(int i = 0; i < 11; i++){
            this.putBelow(new Tile(TypeOfTile.GREEN));
        }
        for(int i = 0; i < 7; i++){
            this.putBelow(new Tile(TypeOfTile.RED));
        }
    }
}
