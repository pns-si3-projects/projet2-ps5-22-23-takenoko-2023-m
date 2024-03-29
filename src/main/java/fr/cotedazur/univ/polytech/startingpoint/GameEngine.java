package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    Board board;
    List<Bot> playerList = new ArrayList<>();

    /**
     * The constructor of the class
     * @param board
     * @param players
     */
    public GameEngine(Board board, List<Bot> players){
        this.board = board;
        this.playerList = players;
    }

    /**
     * The method to launch the game and stop it when one of the player got 9 objectives realised
     */
    public void launchGame(){
    //On boucle sur le jeu
        int indexPlayer = 0;
        int nbTour = 1;
        boolean isGameFinished = false;
        while(true){
            String message = "<       > Tour numero : "+nbTour+" <       >";
            Main.LOGGER.info(message);
            this.playerList.get(indexPlayer).play();
            if (this.playerList.get(indexPlayer).getNbObjectifsRealises() >=9){
                isGameFinished = true;
                this.playerList.get(indexPlayer).setPoint(playerList.get(indexPlayer).getPoint()+2);
            }
            indexPlayer +=1;
            if(indexPlayer == this.playerList.size()){
                indexPlayer=0;
                nbTour++;
            }
            if(nbTour == 50){
                Main.LOGGER.info("Nombre de tour max atteint");
                break;
            }
            if(isGameFinished){
                int points = -1;
                boolean verif = false;
                Bot ret = null;
                for(Bot b : playerList){
                    if(b.getPoint()>points){
                        ret = b;
                        points = b.getPoint();
                    }
                    verif = b.getPoint()==points;
                }
                playerList.remove(ret);
                printWinner(ret,verif,playerList.get(0).getPoint());
                break;
            }
        }
    }

    /**
     * This method will print the winner
     * @param p The winner
     * @param isEgality a boolean to verify if there is an egality
     * @param pointsPerdant The number of points of the looser
     */
    public void printWinner(Bot p, boolean isEgality, int pointsPerdant){
        String message = "Le joueur est gagnant est : "+p.getNom()+" avec un score de "+p.getPoint()+" points marques \nLe perdant possède "+pointsPerdant+" points";
        if(!isEgality) Main.LOGGER.info(message);
        else Main.LOGGER.info("Egalite entre les deux joueurs ! ");
    }
}
