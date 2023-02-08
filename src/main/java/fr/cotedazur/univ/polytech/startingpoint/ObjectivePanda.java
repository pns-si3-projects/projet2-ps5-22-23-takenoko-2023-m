package fr.cotedazur.univ.polytech.startingpoint;

import fr.cotedazur.univ.polytech.startingpoint.bots.Bot;
import fr.cotedazur.univ.polytech.startingpoint.bots.IntermediateBot;
import fr.cotedazur.univ.polytech.startingpoint.bots.PrimaryBot;

public class ObjectivePanda extends ObjectifWithOneColor implements ObjectiveInterface{

        private String type = "panda";

        private int nbToEat;
        private int nbPointsWin;
        private int complexity;

        public ObjectivePanda(String type, int nbToEat, int nbPointsWin, TypeOfTile typeOfTile) {
            super(typeOfTile);
            this.type = type;
            this.nbToEat = nbToEat;
            this.nbPointsWin = nbPointsWin;
        }
    public void setComplexity(int complexity){
        if(this.complexity>complexity){
            this.complexity = complexity;
        }
    }
    public int getComplexity(){
        return this.complexity;
    }


        public int getNbPointsWin() { return this.nbPointsWin; }

        public boolean isValid(Bot p, Board b){
            if(p.getNbBamboo(this.typeOfTile)>=this.nbToEat){
                p.resetNbBamboo(this.typeOfTile);
                return true;
            }else{
                return false;
            }
        }

    @Override
    public void play(PrimaryBot player) {
        player.playForPandaCard();
    }

    public void play(IntermediateBot player) { player.playForPandaCard(); }


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
