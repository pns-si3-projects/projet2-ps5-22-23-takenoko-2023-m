package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Main {

    public static void main(String... args) {
        Board board = new Board();
        ObjectivePlot objectivePlot = new ObjectivePlot("line2");
        Player bot1 = new Player(board, "Robot 1", objectivePlot);
        ArrayList<Player> listPlayer = new ArrayList<>();
        listPlayer.add(bot1);
        GameEngine game = new GameEngine(board, listPlayer);
        game.launchGame();

    }
}
