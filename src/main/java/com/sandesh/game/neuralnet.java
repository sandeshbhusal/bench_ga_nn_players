package com.sandesh.game;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.MultiLayerPerceptron;

public class neuralnet extends Player {
    NeuralNetwork network;
    
    neuralnet(String neuralnetfile) {
        super();
        
        try {
            network = NeuralNetwork.createFromFile("./models/sandesh.model");
        } catch (Exception e) {
            System.err.println("Couldn't find the neural net file. Creating one.");
            network = new MultiLayerPerceptron(10, 16, 8, 1);
            network.save("./models/sandesh.model");
        }
    }

    @Override
    int make_move() {
        if (network == null) {
            System.err.println("Network is null");
        }

        int bestCell = this.board.possible_moves().get(0);
        double bestOutput = Double.NEGATIVE_INFINITY;
        for (int possible_cell : this.board.possible_moves()) {
            int[] temp_board = this.board.board.clone();
            temp_board[possible_cell] = this.myplayernumber;
            double[] input = new double[10];
            input[0] = myplayernumber;
            for (int i = 1; i < temp_board.length; i++)
                input[i] = (double)temp_board[i];

            network.setInput(input);
            double output = network.getOutput()[0];
            if (output > bestOutput) {
                bestCell = possible_cell;
                bestOutput = output;
            }
        }

        network.save("./models/sandesh.model");

        return bestCell;
    }
}
