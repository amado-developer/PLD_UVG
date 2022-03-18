package PLD;

import PLD.classes.FA;
import PLD.classes.Lexer;
import PLD.classes.Thompson;
import java.io.IOException;
import java.util.Scanner;
public class Main {

    private static String menu(){
        return "1. NFA from Regex \n" + "2. DFA from NFA (Subsets)\n" +  "3 or else to exit";
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner reader = new Scanner(System.in);
        boolean keep = true;
        FA nfa = null;
        String regex = null;
        while (keep){
            System.out.println(menu());
            String option = reader.nextLine();

            switch (option){

                case "1" -> {
                    System.out.println("Insert the Regular Expresion");
                    regex = reader.nextLine();
                    regex = Lexer.preProcessString(regex);
                    Thompson thompson = new Thompson(regex);
                    nfa = thompson.executeAlgorithm();
                    System.out.println("Insert the Word");
                    boolean isAccepted = nfa.simulateAFN(reader.nextLine());
                    if(isAccepted){
                        System.out.println("String is Accepted");
                    }else {
                        System.out.println("String is Rejected");
                    }
                }
                case "2" -> {
                    try{
                        FA dfa = nfa.convertToSubSets(regex);
                        System.out.println("Insert the word to Test");
                        dfa.graph(dfa, true);
                        boolean isDFAAccepted = dfa.simulateAFD(reader.nextLine());
                        if(isDFAAccepted){
                            System.out.println("String is Accepted");
                        }else {
                            System.out.println("String is Rejected");
                        }

                    }catch (Exception e){
                        System.out.println("Can't create before NFA");
                    }
                }
                default -> {
                    System.out.println("Bye");
                    keep = false;
                }
            }
        }
    }
}
