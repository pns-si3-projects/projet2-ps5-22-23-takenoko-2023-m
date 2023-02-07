package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;

public class ObjectivePanda extends ObjectifWithOneColor implements ObjectiveInterface{

        private String type = "panda";

        private int nbToEat;
        private int nbPointsWin;

        public ObjectivePanda(String type, int nbToEat, int nbPointsWin, TypeOfTile typeOfTile) {
            super(typeOfTile);
            this.type = type;
            this.nbToEat = nbToEat;
            this.nbPointsWin = nbPointsWin;
        }


        public int getNbPointsWin() { return this.nbPointsWin; }

        public boolean isValid(Bot p, Board b){
            return p.getNbBamboo(this.typeOfTile)>=this.nbToEat;   //fixed bug in case the player already has bamboo and the amount is higher
        }

    @Override
    public void play(Bot player) {
        player.playForPandaCard();
    }


    public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getNbToEat(){
            return this.nbToEat;
        }
        public String toString(){
            return "Objectif de type "+this.type + " et de nombre de bambou a manger " + this.nbToEat;
        }

}
