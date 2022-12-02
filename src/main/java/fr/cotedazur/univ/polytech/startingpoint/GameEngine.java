package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class GameEngine {
    Board board;
    ArrayList<Player> playerList = new ArrayList<>();

    public GameEngine(Board board, ArrayList<Player> players){
        this.board = board;
        this.playerList = players;
    }

    public void launchGame(){
        int nbTour = 1;
        int indexPlayer = 0;
        while(true){
            //this.playerList.get(indexPlayer).play()
            //if(this.playerList.get(indexPlayer).getNbObjectifsRealises == 9){
                //Print winner
                //break
            //}
            indexPlayer++;
            if(indexPlayer == this.playerList.size()){
                nbTour++;
                indexPlayer=0;
            }
        }
    }
    public void printWinner(Player p){}
}
