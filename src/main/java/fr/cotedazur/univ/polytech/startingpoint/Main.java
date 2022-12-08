package fr.cotedazur.univ.polytech.startingpoint;

public class Main {

    public static void main(String... args) {
        Board board = new Board();
        ObjectivePlot objectivePlot = new ObjectivePlot("line2");
        Player bot1 = new Player(board,"Robot 1",objectivePlot);
        int x= 0;
        int y = 0;
        bot1.addTile(new Tile(x,y));
        while(bot1.isObjectiveValid() == false){
            System.out.println(bot1.playToAchieveObjective());

            bot1.setPoint(bot1.getPoint()+1);
        }
        System.out.println("La partie est terminée");
    }


}
