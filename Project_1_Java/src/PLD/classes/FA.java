package PLD.classes;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class FA {
    private ArrayList<String> alphabet;
    private ArrayList<State> states;

    public FA(ArrayList<String> alphabet, ArrayList<State> states) {
        this.alphabet = alphabet;
        this.states = states;
    }

    public FA(FA obj) {
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

    public void graph(FA finalSM, boolean isDFA) throws IOException {
        ArrayList<String> graph = new ArrayList<>();
        for(State state: finalSM.getStates()){
            if(state.isFinal()){
                graph.add(state.getId());
                if(!isDFA){
                    continue;
                }
            }
            for(Transition transition: state.getTransitions()){
                if(isDFA){
                    graph.add(state.getId() + "|" + transition.getKey() + "|" + transition.getValue() + "|" + state.isFinal());
                }
                else{
                    graph.add(state.getId() + "|" + transition.getKey() + "|" + transition.getValue());
                }
            }
        }

        try {
            FileWriter writer = new FileWriter("FA.txt");
            for(String line: graph){
                writer.write(line + "\n");
            }

            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        if(isDFA)
            Runtime.getRuntime().exec("/usr/local/bin/python DFA.py");
        else
            Runtime.getRuntime().exec("/usr/local/bin/python NFA.py");
    }
    private ArrayList<State> eClosure(ArrayList<State> states){
        ArrayList<State> temp = new ArrayList<>();
        while (states.size() > 0) {
            ArrayList<State> eClosureStates = new ArrayList<>();
            for (State currentState : states) {
                if(currentState.isFinal() && !eClosureStates.contains(currentState)){
                    eClosureStates.add(currentState);
                }
                for (Transition transition : currentState.getTransitions()) {
                    if (transition.getValue().equals(Thompson.EPSILON)) {
                        State tempState = this.states.stream()
                                .filter(s -> transition.getKey().equals(s.getId()))
                                .findAny()
                                .orElse(null);
                        if (tempState != null) {
                            if(!eClosureStates.contains(tempState)){
                                if(!eClosureStates.contains(currentState)){
                                    eClosureStates.add(currentState);
                                }
                                eClosureStates.add(tempState);
                                temp.add(tempState);
                            }else{
                                if(!eClosureStates.contains(currentState))
                                    eClosureStates.add(currentState);
                            }
                        }
                    } else {
                        if(!eClosureStates.contains(currentState))
                            eClosureStates.add(currentState);
                    }
                }
            }

            Collections.sort(eClosureStates);

            if(states.equals(eClosureStates)){
                return eClosureStates;
            }
            states = eClosureStates;
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

    public void convertToSubSets() throws IOException {
        ArrayList<ArrayList<State>> dStates = new ArrayList<>();
        ArrayList<State> states = new ArrayList<>();
        ArrayList<State> AFDStates = new ArrayList<>();
        State newState;
        State s0 = this.states.stream()
                .filter(s -> "s0".equals(s.getId()))
                .findAny()
                .orElse(null);
        states.add(s0);
        dStates.add(eClosure(states));

        char [] alphabet = {'a', 'b', 'c'};
        for (int i = 0; i < dStates.size(); i++) {
            ArrayList<Transition> transitions = new ArrayList<>();
            int counter = i + 1;
            for(char character: alphabet){
                ArrayList<State> newStates = eClosure(move(dStates.get(i), character));
                if(dStates.contains(newStates)){
                    ArrayList<State> checkState = dStates.stream()
                            .filter(newStates::equals)
                            .findAny()
                            .orElse(null);
                    int index = dStates.indexOf(checkState);
                    transitions.add(new Transition("s" + index, character));
                }else {
                    if(newStates.size() == 0){
                        counter--;
                        continue;
                    }
                    if(i > 0){
                        transitions.add(new Transition("s" + (dStates.size()), character));
                    }else {
                        transitions.add(new Transition("s" + counter, character));
                    }

                    dStates.add(newStates);
                    counter++;
                }

            }
            boolean isFinal = false;
            String finalState = this.states.get(this.states.size() - 1).getId();
            for(State st : dStates.get(i)) {
                if (st.getId().equals(finalState)) {
                    isFinal = true;
                    break;
                }
            }

            if(i == 0){
                AFDStates.add(new State("s" + i, transitions, isFinal, true ));
            }else{
                AFDStates.add(new State("s" + i, transitions, isFinal, false ));
            }
        }

        graph(new FA(new ArrayList<>(), AFDStates), true);
    }
}
