package main.model;

// Represent a square on the board
//
public class Square {
    private String value = "";

    public Square() {
        //stub
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSquareEmpty() {
        return value == null;
    }

    public String getValue() {
        return value;
    }
}
