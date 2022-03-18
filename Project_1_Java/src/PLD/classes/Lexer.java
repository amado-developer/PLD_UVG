package PLD.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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

    public static ArrayList<Character> set_alphabet(String regex){
        ArrayList<Character> alphabet = new ArrayList<>();

        for(char character: regex.toCharArray()){
            if(!alphabet.contains(character) && isConcat(character)){
                alphabet.add(character);
            }
        }
        Collections.sort(alphabet);
        return alphabet;
    }

    private static boolean isConcat(char character){
        switch (character) {
            case '|', '&', '?', '+', '(', ')', '*' -> {
                return false;
            }
            default -> {
                return true;
            }
        }
    }
    public static String preProcessString(String regex){
        StringBuilder newRegex = new StringBuilder();
        for (int i = 0; i < regex.length(); i++) {
            newRegex.append(regex.charAt(i));
            if(i < regex.length() - 1){
                if(regex.charAt(i) == ')' && isConcat(regex.charAt(i + 1))){
                    newRegex.append("&");
                }else if(regex.charAt(i) == '*' && isConcat(regex.charAt(i + 1))){
                    newRegex.append("&");
                }else if(isConcat(regex.charAt(i)) && regex.charAt(i + 1) == '('){
                    newRegex.append("&");
                }else if(isConcat(regex.charAt(i)) && isConcat(regex.charAt(i + 1))){
                    newRegex.append("&");
                }
            }
        }
    return String.valueOf(newRegex);
    }

    public int getRegexLength() {
        return regex.length();
    }
}
