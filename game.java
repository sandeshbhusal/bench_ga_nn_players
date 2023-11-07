public class game {
    public static void main(String[] args) {
        randomPlayer r1 = new randomPlayer();
        randomPlayer r2 = new randomPlayer();

        int p1wins = 0;
        int p2wins = 0;
        int draws = 0;
        for (int i = 0; i < 10000; i++) {
            switch (tournament(r1, r2)) {
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

    static public int tournament(player p1, player p2) {
        tic_tac_toe board = new tic_tac_toe();
        p2.board = board;
        p1.board = board;

        player players[] = new player[2];
        players[0] = p1;
        players[1] = p2;

        int playerindex = 1;

        int winner = -1;

        while (!board.gameover()) {
            player p = players[playerindex];
            playerindex = (playerindex + 1) % 2;
            board.make_move(playerindex + 1, p.make_move());
            if (board.gamewin()) {
                winner = playerindex;
                break;
            }
        }

        if (winner >= 0) {
            return winner + 1;
        }
        return 0;
    }
}
