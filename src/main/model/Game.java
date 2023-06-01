package main.model;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;

    public Game() {
        board = new Board();
        player1 = new Player("X");
        player2 = new Player("O");
    }

    public Board getBoard() {
        return this.board;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }
}
