package main.ui;

import main.model.Game;

public class SimpleGame {
    private Game game;

    public SimpleGame() {
        this.game = new Game();
    }

    public static void main(String[] args) {
        SimpleGame simpleGame = new SimpleGame();
        SimpleUI simpleUI = new SimpleUI(simpleGame.getGame());

        System.out.println("Welcome to Tic-Tac-Toe!\n");
        simpleUI.runUI();
        simpleUI.quitProgram();
        System.out.println("Hope you enjoyed the game. Goodbye!");

    }

    public Game getGame() {
        return this.game;
    }
}
