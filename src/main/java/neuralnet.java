import java.io.File;


public class neuralnet extends Player {
    File neuralnetfile;

    neuralnet(String neuralnetfile) {
        super();
        try {
            this.neuralnetfile = new File(neuralnetfile);
            // If the neuralnetfile does not exist, create a new neural net,
            // tie it to that file, and open the neural net.
            
        } catch (Exception e) {
            System.err.println("Couldn't find the neural net weights file.");
        }
    }

    // Update the weights by reading the data.csv file and fitting on the new data.
    void update_neuralnet() {

    }

    @Override
    int make_move() {
        // Simply, feed the possible board states to the neural net,
        // and get the max value of the possible board state.
        for (int possible_cell: this.board.possible_moves()) {
            int[] temp_board = this.board.board.clone();
        }
        return 0;
    }
}
