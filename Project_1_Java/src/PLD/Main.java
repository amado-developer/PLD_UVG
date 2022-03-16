package PLD;

import PLD.classes.Lexer;
import PLD.classes.Parser;
import PLD.classes.Thompson;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String regex= "((a|b)|c)*";
        Thompson thompson = new Thompson(regex);
        thompson.executeAlgorithm();
    }

}
