package main.ui;

import java.util.Scanner;
import main.model.Board;
import main.model.Game;
import main.model.Player;

public class SimpleUI {
    private Board board;
    private final Player player1;
    private final Player player2;

    private String str;
    private Scanner input;
    private boolean runGame;
    private boolean gameOver;
    private boolean whichPlayer = true; // true for 1, false for 2
    private int numOfGames = 0;

    public SimpleUI(Game game) {
        board = new Board();
        player1 = game.getPlayer1();
        player2 = game.getPlayer2();
        input = new Scanner(System.in);
        runGame = false;
        gameOver = false;
    }

    public void runUI() {
        printInstructions();
        getPlayerInfo();
        runGame();
    }

    private void runGame() {
        while (runGame) {
            numOfGames++;
            whichPlayer = numOfGames % 2 != 0;
            System.out.println("Let's play!");
            while (!gameOver) {
                printBoardState();
                getPlayerMove();
                if (board.hasWon() || !board.moreMove()) {
                    gameOver = true;
                }
            }

            if (board.hasWon()) announceWinner();
            restartGame();
        }
    }

    private void announceWinner() {
        String player = player1.getName();
        if (whichPlayer) {
            player = player2.getName();
            player2.updateGamesWon();
        } else {
            player1.updateGamesWon();
        }
        System.out.println("Congratulations " + player + ". You are the winner!");
        System.out.print("     ");
        System.out.println(player1.getName() + " " + player1.getGamesWon() + " - " +
            player2.getGamesWon() + " " + player2.getName());
    }

    private void restartGame() {
        System.out.println("Rematch? [y/n]: ");
        String start = input.next();
        if (start.equals("y") || start.equals("yes")) {
            board = new Board();
            gameOver = false;
            runGame();
        } else if (start.equals("n") || start.equals("no")) {
            runGame = false;
        } else {
            System.out.println("Unknown input :(");
        }

    }

    private void getPlayerMove() {
        String player = player1.getName();
        String value = "X";
        if (!whichPlayer) {
            player = player2.getName();
            value = "O";
        }
        System.out.print(player + " (" + value + ")" +
            " choose the square for your number (1 - 9): ");
        int num = input.nextInt();
        int i = indexX(num);
        int j = indexY(num);
        if (board.emptySquare(i, j)) {
            board.updateMove(indexX(num), indexY(num), value);
            whichPlayer = !whichPlayer;
        } else {
            System.out.println("Square already taken");
            getPlayerMove();
        }
    }

    // print instructions to begin game
    private void printInstructions() {
        System.out.println("Instructions: "
            + "Player 1 is X. Player 2 is O. Player 1 goes first."
        );
        System.out.println("Start game? [y/n]: ");
        String start = input.nextLine();
        if (start.equals("y") || start.equals("yes")) {
            runGame = true;
        }
    }

    private void getPlayerInfo() {
        System.out.println("Enter Player 1's name:");
        String name1 = input.nextLine();
        player1.setName(name1);
        System.out.println("Enter Player 2's name:");
        String name2 = input.nextLine();
        player2.setName(name2);
    }

    private void printBoardState() {
        System.out.println("Current Board");
        int num = 1;
        for (int i = 0; i < 3; i++) {
            System.out.print("     ");
            for (int j = 0; j < 3; j++) {
                String value = String.valueOf(num);
                if (!board.emptySquare(i, j)) {
                    value = board.getValue(i, j).toString();
                }
                System.out.print("[" + value + "]");
                num++;
            }
            System.out.println("");
        }
    }

    // EFFECTS: print instructions to quit program
    private void quit() {
        System.out.println("To quit at any time, enter 'quit'.");
    }

    // EFFECTS: stops receiving user input
    public void quitProgram() {
        input.close();
    }

    // turn 1d index to 2d
    public int indexX(int num) {
        return (num - 1) / 3;
    }

    public int indexY(int num) {
        return (num - 1) % 3;
    }

    // EFFECTS: remove whitespace around s and make inputs uniformed
    private String appropriateInput(String s) {
        s = s.toLowerCase();
        s = s.trim();
        return s;
    }

}
