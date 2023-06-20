package main.ui;

import java.util.Scanner;
import java.util.Vector;
import main.model.Board;
import main.model.Player;

public class AIMasterUI {
    // Currently default AI = "X", Player = "O"
    private Board board;
    static final int AI = 1;
    static final int PLAYER = -1;
    private int whichPlayer = 1; // 1 for AI, -1 for Player

    private Player player1; // Player
    private Player computer; // AI

    private String str;
    private Scanner input;
    private boolean runGame;
    private boolean gameOver;
    private int numOfGames = 0;

    public AIMasterUI() {
        board = new Board();
        input = new Scanner(System.in);
        runGame = false;
        gameOver = false;
    }

    public void runUI() {
        printInstructions();
        runGame();
    }

    private void runGame() {
        while (runGame) {
            numOfGames++;
            if (numOfGames % 2 == 1) whichPlayer = -1;
            else whichPlayer = 1;
            System.out.println("Let's play!");
            while (!gameOver) {
                if (board.hasWon() || !board.moreMove()) {
                    gameOver = true;
                    break;
                }
                printBoardState();
                getPlayerMove();
            }

            if (board.hasWon()) announceWinner();
            restartGame();
        }
    }

    private void announceWinner() {
        printBoardState();
        if (board.hasWon()) {
            if (whichPlayer == 1) {
                player1.updateGamesWon();
                System.out.println(
                    "Congratulations " + player1.getName() + ". You are the winner!");
            } else {
                computer.updateGamesWon();
                System.out.println("Computer wins!");
            }
        } else {
            System.out.println("It's a tie!");
        }
        System.out.print("     ");
        System.out.println(player1.getName() + " " + player1.getGamesWon() + " - " +
            computer.getGamesWon() + " Computer");
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
        if (whichPlayer == -1) {
            String player = player1.getName();
            String value = player1.getSymbol();
            System.out.print(player + " (" + value + ")" +
                " choose the square for your number (1 - 9): ");
            int num = input.nextInt();
            int i = indexX(num);
            int j = indexY(num);
            if (board.emptySquare(i, j)) {
                board.updateMove(indexX(num), indexY(num), value);
                whichPlayer = -whichPlayer;
            } else {
                System.out.println("Square already taken");
                getPlayerMove();
            }
        } else {
            System.out.println("Computer has played.");
            Vector<Vector<Integer>> moves = possibleMoves();
            int count = moves.size();
//            Random rand = new Random();
            int x, y;
            if (count == 9) {
                x = 1;
                y = 1;
            } else {
                x = minimax(count, 1)[0];
                y = minimax(count, 1)[1];
            }
            board.updateMove(x, y, computer.getSymbol());
            whichPlayer = -whichPlayer;
        }
    }

    // print instructions to begin game
    private void printInstructions() {
        System.out.println("You are playing against a computer "
            + "and you can start first. \n" + "Do you want to be [X] or [O]? (Enter 1 or 2)"
        );
        System.out.println("1. [X]       2. [O]");
        int num = input.nextInt();
        if (num == 1) {
            player1 = new Player("X");
            computer = new Player("O");
        }
        else if (num == 2) {
            player1 = new Player("O");
            computer = new Player("X");
        }
        input.nextLine();
        System.out.println("Enter your name:");
        String name1 = input.nextLine();
        player1.setName(name1);

        System.out.println("Start game? [y/n]: ");
        String start = input.nextLine();
        if (start.equals("y") || start.equals("yes")) {
            runGame = true;
        }
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

    // turn 1d index to 2d
    public int indexX(int num) {
        return (num - 1) / 3;
    }

    public int indexY(int num) {
        return (num - 1) % 3;
    }

    private int checkState() {
        String[][] current = board.getBoard();
        // check rows
        for (int x = 0; x < 3; x++) {
            if (current[x][0] == current[x][1] &&
                current[x][1] == current[x][2] &&
                current[x][0] != "") {
                if (current[x][0] == computer.getSymbol()) return 1;
                else return -1;
            }
        }

        // check columns
        for (int y = 0; y < 3; y++) {
            if (current[0][y] == current[1][y] &&
                current[1][y] == current[2][y] &&
                current[0][y] != "") {
                if (current[0][y] == computer.getSymbol()) return 1;
                else return -1;
            }
        }

        // check diagonal
        if (current[1][1] != "") {
            if (current[0][0] == current[1][1] &&
                current[1][1] == current[2][2]) {
                if (current[1][1] == computer.getSymbol()) return 1;
                else return -1;
            }

            if (current[0][2] == current[1][1] &&
                current[1][1] == current[2][0]) {
                if (current[1][1] == computer.getSymbol()) return 1;
                else return -1;
            }
        }
        return 0;
    }

    private Vector<Vector<Integer>> possibleMoves() {
        Vector<Vector<Integer>> emptySquares = new Vector<Vector<Integer>>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.emptySquare(i, j)) {
                    Vector<Integer> pair = new Vector<Integer>();
                    pair.add(i);
                    pair.add(j);
                    emptySquares.add(pair);
                }
            }
        }
        return emptySquares;
    }

    // Depends on  checkState(), gameOver(), possibleMoves()
    private int[] minimax(int depth, int player) {
        int[] bestMove = new int[3];
        if (player == 1) {
            bestMove[0] = -1;
            bestMove[1] = -1;
            bestMove[2] = Integer.MIN_VALUE;
        } else {
            bestMove[0] = -1;
            bestMove[1] = -1;
            bestMove[2] = Integer.MAX_VALUE;
        }

        if (depth == 0 || board.hasWon() || !board.moreMove()) {
            bestMove[2] = checkState();
            return bestMove;
        }

        Vector<Vector<Integer>> emptySquares = possibleMoves();
        for (int i = 0; i < emptySquares.size(); i++) {
            int x = emptySquares.get(i).get(0);
            int y = emptySquares.get(i).get(1);
            if (player == 1) board.updateMove(x, y, computer.getSymbol());
            else  board.updateMove(x, y, player1.getSymbol());

            int[] score = minimax(depth - 1, -player);
            board.updateMove(x, y, "");
            score[0] = x;
            score[1] = y;
            if (player == 1) {
                if (score[2] > bestMove[2]) bestMove = score; // Max
            } else {
                if (score[2] < bestMove[2]) bestMove = score; // Min
            }
        }
        return bestMove;
    }


}
