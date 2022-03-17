package PLD.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class State implements Comparable<State>{
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

    public State(State obj) {
        this.id = obj.id;
        this.transitions = new ArrayList<>();
        this.isFinal = obj.isFinal;
        this.isInitial = obj.isInitial;

        for(Transition transition: obj.transitions){
            Transition tempTransition = new Transition(transition);
            this.transitions.add(tempTransition);
        }
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

    @Override
    public int compareTo(State o) {
        return this.id.compareTo(o.id);
    }
}
