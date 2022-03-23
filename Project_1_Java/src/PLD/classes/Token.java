package PLD.classes;

public class Token {
    private String id;
    private char value;
    private int precedence;

    public Token(String id, char value, int precedence){
        this.id = id;
        this.value = value;
        this.precedence = precedence;
    }

    public String getId() {
        return id;
    }

    public char getValue() {
        return value;
    }

    public int getPrecedence() {
        return precedence;
    }
}
