import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import ai.djl.*;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.nn.*;
import ai.djl.nn.core.*;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;

public class neuralnet extends Player {
    File neuralnetfile;
    Model neuralnet;

    neuralnet(String neuralnetfile) {
        super();
        try {
            this.neuralnetfile = new File(neuralnetfile);
            neuralnet.load(Paths.get("./models/"), neuralnetfile);
        } catch (Exception e) {
            System.err.println("Couldn't find the neural net weights file.");
            SequentialBlock block = new SequentialBlock();
            block.add(Linear.builder().setUnits(11).build());
            block.add(Activation::relu);
            block.add(Linear.builder().setUnits(16).build());
            block.add(Activation::relu);
            block.add(Linear.builder().setUnits(1).build());

            Model model = Model.newInstance("tictactoe");
            model.setBlock(block);
            this.neuralnet = model;

            try {
                model.save(Paths.get("./models/"), neuralnetfile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.err.println("Initialized and saved the model.");
        }
    }

    @Override
    int make_move() {
        // Simply, feed the possible board states to the neural net,
        // and get the max value of the possible board state.

        // Translator
        Translator<int[], float> t = new Translator<int[], int[]>() {
            @Override
            public NDList processInput(TranslatorContext ctx, int[] input) throws Exception {
                NDManager manager = NDManager.newBaseManager();
                NDArray x = manager.create(input);
                return new NDList(x);
            }

            @Override
            public int[] processOutput(TranslatorContext ctx, NDList list) throws Exception {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'processOutput'");
            }
        };

        var p = neuralnet.newPredictor(t);

        for (int possible_cell : this.board.possible_moves()) {
            int[] temp_board = this.board.board.clone();
            temp_board[possible_cell] = this.myplayernumber;

            try {
                float value = p.predict(temp_board);
            } catch (TranslateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return 0;
    }
}
