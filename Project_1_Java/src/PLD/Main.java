package PLD;

import PLD.classes.NFA;
import PLD.classes.Thompson;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String regex= "(b|b)*&a&b&b&(a|b)*";
        Thompson thompson = new Thompson(regex);
        NFA nfa = thompson.executeAlgorithm();
        boolean isAccepted = nfa.simulate("abb");
        if(isAccepted){
            System.out.println("String is Accepted");
        }else {
            System.out.println("String is Rejected");
        }
    }

}
