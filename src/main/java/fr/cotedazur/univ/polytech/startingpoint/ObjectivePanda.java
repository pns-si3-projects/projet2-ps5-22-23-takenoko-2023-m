package fr.cotedazur.univ.polytech.startingpoint;

public class ObjectivePanda implements ObjectiveInterface{

        private String type = "panda";

        private int nbToEat;

        private boolean validity = false;

        public ObjectivePanda(String type, int nbToEat){
            this.type = type;
            this.nbToEat = nbToEat;
        }

        public void setValid(){
            this.validity = true;
        }


        public boolean isValid(Board b){
            return this.validity;
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
            return "Objectif de type "+this.type + " et de nombre de bambou à manger " + this.nbToEat;
        }

}
