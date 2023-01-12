package fr.cotedazur.univ.polytech.startingpoint;
import java.util.ArrayList;

public class Main {

    public static void main(String... args) {
        Board board = new Board();

        ObjectivePlot objectivePlot = new ObjectivePlot("line2", 1);
        ObjectiveGardener objectiveGardener = new ObjectiveGardener("gardener", 4, 7);
        ObjectivePanda objectivePanda = new ObjectivePanda("panda", 1, 8);
        ArrayList<ObjectiveInterface> objectives = new ArrayList<ObjectiveInterface>();
        objectives.add(objectivePlot);
        objectives.add(objectiveGardener);
        objectives.add(objectivePanda);
        Player bot1 = new Player(board, "Quentin", objectives);


        ObjectivePlot objectivePlot1 = new ObjectivePlot("line2",6);
        ObjectiveGardener objectiveGardener1 = new ObjectiveGardener("gardener",2,7);
        ObjectivePanda objectivePanda1 = new ObjectivePanda("panda",3,5);
        ArrayList<ObjectiveInterface> objectives1 = new ArrayList<>();
        objectives1.add(objectivePlot1);
        objectives1.add(objectiveGardener1);
        objectives1.add(objectivePanda1);
        Player bot2 = new Player(board, "Simon", objectives1);

        ArrayList<Player> listPlayer = new ArrayList<>();
        listPlayer.add(bot1);
        listPlayer.add(bot2);
        GameEngine game = new GameEngine(board, listPlayer);
        game.launchGame();
    }
}