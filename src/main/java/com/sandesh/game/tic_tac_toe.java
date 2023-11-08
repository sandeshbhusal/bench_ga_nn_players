package com.sandesh.game;
import java.util.ArrayList;

abstract class Player {
    tic_tac_toe board;
    int myplayernumber;
    abstract int make_move();
}

public class tic_tac_toe {
    int board[];

    tic_tac_toe() {
        this.board = new int[9];
    }

    public int make_move(int player, int cell) {
        if (board[cell] != 0 ){
            return -1;
        }
        board[cell] = player;

        return 0;
    }

    public boolean gameover() {
        return this.possible_moves().size() == 0;
    }

    public boolean gamewin() {
        // Horizontal check
        for (int i = 0; i < 9; i += 3) {
            if (board[i] != 0 && board[i] == board[i + 1] && board[i] == board[i + 2]) {
                return true;
            }
        }

        // Vertical check
        for (int i = 0; i < 3; i++) {
            if (board[i] != 0 && board[i] == board[i + 3] && board[i] == board[i + 6]) {
                return true;
            }
        }

        // Diagonal check
        if (board[0] != 0 && board[0] == board[4] && board[0] == board[8]) {
            return true;
        }

        if (board[2] != 0 && board[2] == board[4] && board[2] == board[6]) {
            return true;
        }

        return false;
    }

    public ArrayList<Integer> possible_moves() {
        ArrayList<Integer> possible = new ArrayList<>();
        for (int i = 0; i < this.board.length; i++) {
            if (this.board[i] == 0) {
                possible.add(i);
            }
        }

        return possible;
    }
}