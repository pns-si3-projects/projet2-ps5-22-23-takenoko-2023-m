package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class GameEngine {
    Board board;
    List<Bot> playerList = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(GameEngine.class.getName());


    public GameEngine(Board board, List<Bot> players){
        this.board = board;
        this.playerList = players;
    }

    public void launchGame(){
    //On boucle sur le jeu
        int indexPlayer = 0;
        int nbTour = 1;
        while(true){
            LOGGER.info("<       > Tour numero : "+nbTour+" <       >");
            this.playerList.get(indexPlayer).play();
            //On vérifie si un joueur atteint le nombre max de points
            if (this.playerList.get(indexPlayer).getPoint() >= 9){
                printWinner(this.playerList.get(indexPlayer));
                break;
            }
            indexPlayer +=1;
            if(indexPlayer == this.playerList.size()){
                indexPlayer=0;
                nbTour++;
            }
        }
    }

    public void printWinner(Bot p){
        System.out.println("Le joueur est gagnant est : "+p.getNom()+" avec un score de "+p.getPoint()+" points marques");
    }
}
