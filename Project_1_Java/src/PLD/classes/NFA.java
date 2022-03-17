package PLD.classes;

import PLD.Interfaces.FiniteStateMachine;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

public class NFA {
    private ArrayList<String> alphabet;
    private ArrayList<State> states;

    public NFA(ArrayList<String> alphabet, ArrayList<State> states) {
        this.alphabet = alphabet;
        this.states = states;
    }

    public NFA(NFA obj) {
        this.alphabet = obj.alphabet;
        this.states = new ArrayList<>();
        for(State state: obj.states){
            State tempState = new State(state);
            this.states.add(tempState);
        }
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

    public void setAlphabet(ArrayList<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    private ArrayList<State> eClosure(ArrayList<State> states){

        ArrayList<State> temp = new ArrayList<>();
        ArrayList<State> originalState = new ArrayList<>(states);
        while (states.size() > 0) {
            ArrayList<State> eClosureStates = new ArrayList<>();
            for (State currentState : states) {
                if(currentState.isFinal()){
                    eClosureStates.add(currentState);
                }
                for (Transition transition : currentState.getTransitions()) {
                    if (transition.getValue().equals(Thompson.EPSILON)) {
                        State tempState = this.states.stream()
                                .filter(s -> transition.getKey().equals(s.getId()))
                                .findAny()
                                .orElse(null);
                        if (tempState != null) {
                            if(!eClosureStates.contains(tempState))
                                eClosureStates.add(tempState);
                                temp.add(tempState);
                        }
                    } else {
                        if(!eClosureStates.contains(currentState))
                            eClosureStates.add(currentState);
                    }
                }
            }

            states = eClosureStates;
            if(temp.size() == 0){
                return eClosureStates;
            }
            temp.clear();
        }
        return  new ArrayList<>();
    }

    private ArrayList<State> move(ArrayList<State> states, char character){
        ArrayList<State> moveStates = new ArrayList<>();
        for(State currentState: states){
            for(Transition transition: currentState.getTransitions()){
                if(transition.getValue().equals(character)){
                    State tempState = this.states.stream()
                            .filter(s -> transition.getKey().equals(s.getId()))
                            .findAny()
                            .orElse(null);
                    if(tempState != null){
                        if(!moveStates.contains(tempState))
                            moveStates.add(tempState);
                    }else {
                        if(!moveStates.contains(currentState))
                            moveStates.add(currentState);
                    }
                }

            }
        }

        return moveStates;
    }
    public Boolean simulate(String string){

        ArrayList<State> currentStates = new ArrayList<>();
        State state = states.stream()
                .filter(s -> "s0".equals(s.getId()))
                .findAny()
                .orElse(null);
        currentStates.add(state);
        currentStates = eClosure(currentStates);

        for(char character: string.toCharArray()){
            currentStates = eClosure(move(currentStates, character));
        }

        return currentStates.contains(this.states.get(this.states.size() - 1));
    }

}
