package PLD.classes;

import java.util.ArrayList;
import java.util.Arrays;

public class Lexer {
    private String regex;
    private int position = 0;

    public Lexer(String regex) {
        this.regex = regex;
    }

    /***
     * Function to generate the token table
     * @return A Token Object
     */
    public Token getToken() {
        if(position < regex.length()){
            char character = this.regex.charAt(this.position);
            this.position++;
            switch (character) {
                case '(':  return new Token("OPEN_PAR", character);
                case ')':  return new Token("CLOSE_PAR", character);
                case '*':  return new Token("KLEAN", character);
                case '+':  return new Token("PLUS", character);
                case '?':  return new Token("QUESTION_MARK", character);
                case '|':  return new Token("OR", character);
                default: return new Token("CHAR", character);
            }
        }
        return null;
    }


    public ArrayList<String> get_alphabet(){
        //TODO
        return null;
    }

    public int getRegexLength() {
        return regex.length();
    }
}
