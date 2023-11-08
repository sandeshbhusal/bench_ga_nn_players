import java.io.File;
import java.io.IOException;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class neuralnet extends Player {
    MultiLayerNetwork model;

    neuralnet(String neuralnetfile) {
        super();
    }

    @Override
    int make_move() {
        MultiLayerNetwork model;
        try {
            model = MultiLayerNetwork.load(new File("./models/sandesh"), false);
        } catch (IOException e) {
            System.err.println("No model was found. Creating a new model..");

            MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder().weightInit(WeightInit.LECUN_NORMAL)
                    .list().layer(0, new DenseLayer.Builder().nIn(10).nOut(16).activation(Activation.RELU).build())
                    .layer(1, new DenseLayer.Builder().nIn(16).nOut(1).activation(Activation.RELU).build())
                    .build();

            model = new MultiLayerNetwork(conf);
            model.init();
            try {
                model.save(new File("./models/sandesh"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        int bestCell = this.board.possible_moves().get(0);
        double bestOutput = Double.NEGATIVE_INFINITY;
        for (int possible_cell : this.board.possible_moves()) {
            int[] temp_board = this.board.board.clone();
            temp_board[possible_cell] = this.myplayernumber;
            INDArray input = Nd4j.create(temp_board);

            double output = model.output(input).getDouble(0);
            if (output >= bestOutput) {
                bestCell = possible_cell;
            }
        }

        return bestCell;
    }
}
