package PLD.Interfaces;

import PLD.classes.State;

import java.util.Stack;

public interface FiniteStateMachine {
    public Stack<State> getStates();
    public void simulate();
}
