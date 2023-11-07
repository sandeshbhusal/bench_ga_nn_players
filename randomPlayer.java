import java.util.Random;

public class randomPlayer extends player{
    @Override
    int make_move() {
        Random rand = new Random();
        if (this.board.possible_moves().size() == 0) {
            return -1;
        }
        int moveindex = rand.nextInt(100) % this.board.possible_moves().size();
        return this.board.possible_moves().get(moveindex);
    } 
}
