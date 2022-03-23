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
                    long algorithmStart = System.currentTimeMillis();
                    regex = Lexer.preProcessString(regex);
                    Thompson thompson = new Thompson(regex);
                    nfa = thompson.executeAlgorithm();
                    long algorithmEnd = System.currentTimeMillis();
                    long algorithmTime =  ((algorithmEnd - algorithmStart));

                    System.out.println("Insert the word to Test");
                    String word = reader.nextLine();
                    long simulationStart = System.currentTimeMillis();
                    boolean isAccepted = nfa.simulateAFN(word);
                    if(isAccepted){
                        System.out.println("String is Accepted");
                    }else {
                        System.out.println("String is Rejected");
                    }
                    long simulationEnd = System.currentTimeMillis();
                    long simulationTime = ((simulationEnd - simulationStart));
                    System.out.println((algorithmTime  + simulationTime) + " Milliseconds");

                }
                case "2" -> {
                    try{
                        long algorithmStart = System.currentTimeMillis();
                        FA dfa = nfa.convertToSubSets(regex);
                        System.out.println("Insert the word to Test");
                        dfa.graph(dfa, true);
                        long algorithmEnd = System.currentTimeMillis();
                        long algorithmTime = ((algorithmEnd - algorithmStart));
                        String word = reader.nextLine();
                        long simulationStart = System.currentTimeMillis();
                        boolean isDFAAccepted = dfa.simulateAFD(word);
                        if(isDFAAccepted){
                            System.out.println("String is Accepted");
                        }else {
                            System.out.println("String is Rejected");
                        }
                        long simulationEnd = System.currentTimeMillis();
                        long simulationTime = ((simulationEnd - simulationStart));
                        System.out.println((algorithmTime  + simulationTime) + " Milliseconds");

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
