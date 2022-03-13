package PLD.classes;

import java.util.ArrayList;
import java.util.Arrays;

public class Lexer {
    private String regex;
    private String[] operators = new String[]{"(", ")", "*", "+", "?", "|"};
    private int position = 0;

    public Lexer(String regex) {
        this.regex = regex;
//        preProcessRegex();
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

//    private void preProcessRegex() {
//        String [] characters = this.regex.split("");
//        String processedRegex = "";
//
//        for (int i = 0; i < characters.length; i++) {
//            if(i + 1 < characters.length){
//                if(!Arrays.asList(operators).contains(characters[i]) && !Arrays.asList(operators).contains(characters[i + 1])
//                || characters[i].equals(")") && !Arrays.asList(operators).contains(characters[i + 1])){
//
//                    processedRegex += characters[i].equals(")") ? "&" :characters[i] + "&";
//                    continue;
//                }
//            }
//            processedRegex += characters[i];
//        }
//
//        this.regex = processedRegex;
//    }

    public ArrayList<String> get_alphabet(){
        //TODO
        return null;
    }

    public int getRegexLength() {
        return regex.length();
    }
}
