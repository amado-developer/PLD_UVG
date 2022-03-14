package PLD.classes;

import PLD.Interfaces.FiniteStateMachine;

import java.util.ArrayList;
import java.util.Stack;

public class NFA {
    private String[] alphabet;
    private ArrayList<State> states = new ArrayList<>();

    public NFA(String[] alphabet, ArrayList<State> states, String s0, String sf) {
        this.alphabet = alphabet;
        this.states = states;
    }
}
