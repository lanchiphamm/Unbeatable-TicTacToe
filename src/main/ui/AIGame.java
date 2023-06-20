package main.ui;

public class AIGame {
    public static void main(String[] args) {
        AIMasterUI aiMasterUI = new AIMasterUI();

        System.out.println("Welcome to Tic-Tac-Toe!\n");
        aiMasterUI.runUI();
//        AIMasterUI.quitProgram();
        System.out.println("Hope you enjoyed the game. Goodbye!");

    }
}
