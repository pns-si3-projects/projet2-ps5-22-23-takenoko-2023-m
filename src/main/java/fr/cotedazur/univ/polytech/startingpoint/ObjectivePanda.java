package fr.cotedazur.univ.polytech.startingpoint;

public class ObjectivePanda implements ObjectiveInterface{

        private String type = "panda";
        private int nbToEat;
        private int nbPointsWin;
        private TypeOfTile typeOfTile;

        public ObjectivePanda(String type, int nbToEat, int nbPointsWin, TypeOfTile typeOfTile) {
            this.type = type;
            this.nbToEat = nbToEat;
            this.nbPointsWin = nbPointsWin;
            this.typeOfTile = typeOfTile;
        }


        public int getNbPointsWin() { return this.nbPointsWin; }

        public TypeOfTile getTypeOfTile() { return this.typeOfTile; }


        public boolean isValid(Player p, Board b){
            return p.getNbBamboo(this.typeOfTile)>=this.nbToEat;   //fixed bug in case the player already has bamboo and the amount is higher
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
