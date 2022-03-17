package PLD;

import PLD.classes.FA;
import PLD.classes.Thompson;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String regex= "((a|b)|c)*";
        Thompson thompson = new Thompson(regex);
        FA nfa = thompson.executeAlgorithm();
        boolean isAccepted = nfa.simulate("babba");
        if(isAccepted){
            System.out.println("String is Accepted");
        }else {
            System.out.println("String is Rejected");
        }
        nfa.convertToSubSets();
    }

}
