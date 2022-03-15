package PLD.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class State {
    private String id;
    private ArrayList<Transition> transitions;
    private boolean isFinal;
    private boolean isInitial;

    public State(String id, ArrayList<Transition> transitions, boolean isFinal, boolean isInitial) {
        this.id = id;
        this.transitions = transitions;
        this.isFinal = isFinal;
        this.isInitial = isInitial;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public void setInitial(boolean initial) {
        isInitial = initial;
    }
}
