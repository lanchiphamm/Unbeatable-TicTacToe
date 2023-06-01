package main.model;

public class Player {
    private String name;
    private String symbol;
    private int gamesWon = 0;

    public Player(String x) {
        this.symbol = x;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void updateGamesWon() {
        gamesWon++;
    }
    public int getGamesWon() {
        return gamesWon;
    }
}
