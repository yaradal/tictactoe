package com.tictactoe.tictactoe;

import org.springframework.stereotype.Service;

@Service
public class Board {
    private Move[][] moves;
    private Player nextPlayer;
    private boolean done;

    public Board() {
        this.moves = new Move[][]{
                {Move.EMPTY, Move.EMPTY, Move.EMPTY},
                {Move.EMPTY, Move.EMPTY, Move.EMPTY},
                {Move.EMPTY, Move.EMPTY, Move.EMPTY}
        };
        this.nextPlayer = Player.X;
        this.done = false;
    }

    public Winner Play(int row, int column, Player player) throws Exception {
        if (this.done) {
            throw new Exception("the game is over!");
        }
        if ((row < 0 || row > 2) && (column < 0 || column > 2)) {
            throw new Exception("row and column need to be larger or equal to 0 and smaller than 2");
        }
        if (this.moves[row][column] != Move.EMPTY) {
            throw new Exception("tile on board is already used");
        }
        if (player != nextPlayer) {
            throw new Exception("it's not the turn of player " + player.toString());
        }

        this.moves[row][column] = PlayerToMove(player);
        this.nextPlayer = player == Player.X ? Player.O : Player.X;

        Winner winner = CheckIfWon(player);

        if (winner != Winner.NONE) {
            this.done = true;
        }
        return winner;
    }

    private Winner CheckIfWon(Player player) {
        Move move = PlayerToMove(player);

        for (int i = 0; i < 3; i++) {
            // We check rows.
            if (this.moves[i][0] == move && this.moves[i][1] == move && this.moves[i][2] == move) {
                return PlayerToWinner(player);
            }
            // We check columns.
            if (this.moves[0][i] == move && this.moves[1][i] == move && this.moves[2][i] == move) {
                return PlayerToWinner(player);
            }
        }
        // We check the first diagonal.
        if (this.moves[0][0] == move && this.moves[1][1] == move && this.moves[2][2] == move) {
            return PlayerToWinner(player);
        }
        // We check the second one.
        if (this.moves[2][0] == move && this.moves[1][1] == move && this.moves[0][2] == move) {
            return PlayerToWinner(player);
        }

        if (isFull()) {
            return Winner.DRAW;
        }

        return Winner.NONE;
    }

    private boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.moves[i][j] == Move.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private Move PlayerToMove(Player player) {
        return Move.values()[player.ordinal()];
    }

    private Winner PlayerToWinner(Player player) {
        return Winner.values()[player.ordinal()];
    }

    public enum Player {
        X,
        O
    }

    enum Move {
        X,
        O,
        EMPTY
    }

    public enum Winner {
        X,
        O,
        NONE,
        DRAW
    }
}



