package fr.cotedazur.univ.polytech.startingpoint;

public class Main {

    public static void main(String... args) {
        Board board = new Board();
        Player bot1 = new Player(board,"Robot 1");
        int x= 0;
        int y = 0;
        while(bot1.getPoint()<10){
            System.out.println(bot1.addTile(new Tile(x,y)));
            x+=1;
            y+=1;
            bot1.setPoint(bot1.getPoint()+1);
        }
        System.out.println("La partie est terminée");
    }


}
