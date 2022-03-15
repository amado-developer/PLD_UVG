package PLD;

import PLD.classes.Lexer;
import PLD.classes.Parser;
import PLD.classes.Thompson;

public class Main {

    public static void main(String[] args) {
        String regex= "((a|b)|c)*";
        Thompson thompson = new Thompson(regex);
        thompson.executeAlgorithm();
    }

}
