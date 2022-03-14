package PLD.classes;

import java.util.ArrayList;
import java.util.Arrays;

public class Lexer {
    private String regex;
    private String[] operators = new String[]{"(", ")", "*", "+", "?", "|"};
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
                case '(':  return new Token("OPEN_PAR", character,6);
                case ')':  return new Token("CLOSE_PAR", character, 6);
                case '*':  return new Token("KLEAN", character, 5);
                case '+':  return new Token("PLUS", character, 4);
                case '?':  return new Token("QUESTION_MARK", character, 3);
                case '&':  return new Token("CONCAT", character, 2);
                case '|':  return new Token("OR", character, 1);
                default:   return new Token("CHAR", character, 0);
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
