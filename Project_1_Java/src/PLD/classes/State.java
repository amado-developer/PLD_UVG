package PLD.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class State {
    private String id;
    private ArrayList<Transition> transitions = new ArrayList<>();
    private boolean isFinal;
    private boolean isInitial;

    public State(String id, ArrayList<Transition> transitions, boolean isFinal, boolean isInitial) {
        this.id = id;
        this.transitions = transitions;
        this.isFinal = isFinal;
        this.isInitial = isInitial;
    }
}
