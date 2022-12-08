package fr.cotedazur.univ.polytech.startingpoint;

import java.util.ArrayList;

public class Main {

    public static void main(String... args) {
        Board board = new Board();

        Player bot1 = new Player(board, "Robot 1");
        ArrayList<Player> listPlayer = new ArrayList<>();
        listPlayer.add(bot1);
        GameEngine game = new GameEngine(board, listPlayer);
        game.launchGame();

    }
}
