package fr.cotedazur.univ.polytech.startingpoint;
import java.util.ArrayList;

import com.beust.jcommander.JCommander;
public class Main {



    public static void main(String... args) {
        System.out.println(args[0]);

        Args parameters = new Args();
        JCommander.newBuilder()
                .addObject(parameters)
                .build()
                .parse(args);

        if (parameters.twoThousands) {
            System.out.println("2000");
        }
        if (parameters.demo) {
            System.out.println("demo");
        }
        if (parameters.csv) {
            System.out.println("csv");
        }


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