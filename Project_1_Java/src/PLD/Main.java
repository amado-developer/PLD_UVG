package PLD;

import PLD.classes.FA;
import PLD.classes.Thompson;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String regex= "(b|b)*&a&b&b&(a|b)*";
        Thompson thompson = new Thompson(regex);
        FA nfa = thompson.executeAlgorithm();
//        boolean isAccepted = nfa.simulateAFN("babba");
//        if(isAccepted){
//            System.out.println("String is Accepted");
//        }else {
//            System.out.println("String is Rejected");
//        }

        FA dfa = nfa.convertToSubSets();
        boolean isDFAAccepted = dfa.simulateAFD("abb");
        if(isDFAAccepted){
            System.out.println("String is Accepted");
        }else {
            System.out.println("String is Rejected");
        }
    }

}
