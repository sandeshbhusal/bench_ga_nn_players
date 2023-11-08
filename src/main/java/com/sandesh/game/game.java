package com.sandesh.game;

import java.io.FileWriter;
import java.util.HashMap;

public class game {
    public static void main(String[] args) {
        Player p1, p2;
        p1 = new neuralnet("./models/sandesh.model");
        p2 = new randomPlayer();

        int winner = duel(p1, p2);
        System.out.println(winner);
    }

    public static int duel(Player p1, Player p2) {
        tic_tac_toe board = new tic_tac_toe();
        Player players[] = new Player[2];

        p1.myplayernumber = 1;
        p2.myplayernumber = 2;

        players[0] = p1;
        players[1] = p2;

        p1.board = board;
        p2.board = board;

        HashMap<int[], Integer> boards = new HashMap<>();
        int index = 0;
        int winner = -1;

        while (!board.gameover()) {
            Player p = players[index];

            // get move from p1 and then p2.
            int move = p.make_move();
            board.make_move(index + 1, move);
            boards.put(board.board.clone(), index + 1);

            if (board.gamewin()) {
                winner = index + 1;
                break;
            }

            index = (index + 1) % 2;
        }

        // Loop through all baords, and write data to data.csv.
        for (int[] b : boards.keySet()) {
            String st = "";
            int player = boards.get(b);
            st += player + ",";
            for (int k : b) {
                st += k + ",";
            }
            if (winner == -1) {
                st += "0\n";
            } else if (winner == player) {
                st += "1\n";
            } else {
                st += "-1\n";
            }

            // Dump data to file.
            try {
                FileWriter datawriter = new FileWriter("./data.csv", true);
                datawriter.write(st);
                datawriter.close();
            } catch (Exception e) {
                // Do fuckall.
            }
        }

        return winner;
    }
}
