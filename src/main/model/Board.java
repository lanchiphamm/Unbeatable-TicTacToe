package main.model;

public class Board {

    private String[][] board = new String[3][3];

    public Board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    public String[][] getBoard() {
        return this.board;
    }

    public String getValue(int x, int y) {
        return board[x][y];
    }

    public void updateMove(int x, int y, String value) {
        board[x][y] = value;
    }

    public boolean emptySquare(int x, int y) {
        return board[x][y] == "";
    }

    public boolean hasWon() {
        // check rows
        for (int x = 0; x < 3; x++) {
            if (board[x][0] == board[x][1] &&
                board[x][1] == board[x][2] &&
                board[x][0] != "")
                return true;
        }

        // check columns
        for (int y = 0; y < 3; y++) {
            if (board[0][y] == board[1][y] &&
                board[1][y] == board[2][y] &&
                board[0][y] != "")
                return true;
        }

        // check diagonal
        if (board[1][1] != "") {
            if (board[0][0] == board[1][1] &&
                board[1][1] == board[2][2])
                return true;

            if (board[0][2] == board[1][1] &&
                board[1][1] == board[2][0])
                return true;
        }
        return false;
    }

    public boolean moreMove() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == "") return true;
            }
        }
        return false;
    }
}
