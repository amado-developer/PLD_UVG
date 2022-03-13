package PLD.classes;

public class Token {
    private String id;
    private char value;

    public Token(String id, char value){
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public char getValue() {
        return value;
    }
}
