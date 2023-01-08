package fr.cotedazur.univ.polytech.startingpoint;
import java.util.ArrayList;

public class Main {

    public static void main(String... args) {
        Board board = new Board();

        Player bot1 = new Player(board, "Simon");
        Player bot2 = new Player(board, "Damien");

        ArrayList<Player> listPlayer = new ArrayList<>();
        listPlayer.add(bot1);
        listPlayer.add(bot2);
        GameEngine game = new GameEngine(board, listPlayer);
        game.launchGame();
    }
}