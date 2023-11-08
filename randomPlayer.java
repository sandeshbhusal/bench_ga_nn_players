import java.util.Random;

public class randomPlayer extends Player{
    @Override
    int make_move() {
        Random rand = new Random();
        int moveindex = rand.nextInt(100) % this.board.possible_moves().size();
        return this.board.possible_moves().get(moveindex);
    } 
}
