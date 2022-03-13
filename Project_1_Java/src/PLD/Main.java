package PLD;

import PLD.classes.Lexer;
import PLD.classes.Parser;

public class Main {

    public static void main(String[] args) {
        String regex= "a|(a|ab)*a+?bb";
        Lexer lexer = new Lexer(regex);
        Parser parser = new Parser(lexer);
        parser.parse();
    }
}
