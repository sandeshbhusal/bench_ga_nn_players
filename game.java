import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class game {
    public static void main(String[] args) {
        randomPlayer r1 = new randomPlayer();
        randomPlayer r2 = new randomPlayer();

        int p1wins = 0;
        int p2wins = 0;
        int draws = 0;
        for (int i = 0; i < 100; i++) {
            switch (playAgainst(r1, r2)) {
                case 0:
                    draws += 1;
                    break;
                case 1:
                    p1wins += 1;
                    break;
                case 2:
                    p2wins += 1;
                    break;

                default:
                    break;
            }
        }

        System.err.println("Tournament results: " + p1wins + " " + p2wins + " " + draws);
    }

    static public int playAgainst(player p1, player p2) {
        tic_tac_toe board = new tic_tac_toe();
        p2.board = board;
        p1.board = board;

        player players[] = new player[2];
        players[0] = p1;
        players[1] = p2;

        int playerindex = 1;

        int winner = -1;

        ArrayList<int[]> localBoards = new ArrayList<>();

        while (!board.gameover()) {
            int[] row = new int[11];

            player p = players[playerindex - 1];
            playerindex = playerindex % 2 + 1;

            row[0] = playerindex;
            board.make_move(playerindex, p.make_move());

            for (int t = 0; t < board.board.length; t++) {
                row[t + 1] = board.board[t];
            }

            localBoards.add(row);

            // Generate data.
            if (board.gamewin()) {
                if (winner == -1) {
                    row[10] = 0;
                    break;
                }

                if (winner == playerindex) {
                    row[10] = 1;
                } else {
                    row[10] = -1;
                }
                break;
            }
        }

        File datafile = new File("./data.csv");

        try {
            datafile.createNewFile();
        } catch (Exception e) {
        }
        ;

        try (FileWriter myWriter = new FileWriter("./data.csv", true)) {
            // Iterate over the entire row, and check if the player won the game or not.
            for (int[] row : localBoards) {
                if (winner != -1) {
                    if (row[0] == winner) {
                        row[10] = 1;
                    } else {
                        row[10] = -1;
                    }
                }
                for (int t : row) {
                    myWriter.write(t + ", ");
                }

                myWriter.write('\n');
            }

            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (winner >= 0) {
            return winner + 1;
        }
        return 0;
    }
}
