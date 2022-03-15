package PLD.classes;

import PLD.Interfaces.FiniteStateMachine;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NFA {
    private ArrayList<String> alphabet;
    private ArrayList<State> states = new ArrayList<>();

    public NFA(ArrayList<String> alphabet, ArrayList<State> states) {
        this.alphabet = alphabet;
        this.states = states;
    }

    public ArrayList<State> getStates() {
        return states;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public int getStatesNumber(){
        return states.size();
    }
}
