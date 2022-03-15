package PLD.classes;

import java.lang.reflect.Array;
import java.util.*;

public class Thompson {
    private Stack<NFA> nfas = new Stack<>();
    private String regex;
    private Lexer lexer;
    private Parser parser;
    private final char EPSILON = '\u03F5';

    public Thompson(String regex) {
        this.regex = regex;
        this.lexer = new Lexer(regex);
        this.parser = new Parser(lexer);
    }

    public void baseStep(char character) {
        ArrayList<State> states = new ArrayList<>();
        ArrayList<Transition> transitions = new ArrayList<>();
        Transition transition = new Transition();

        transition.addTransition("sf", character);
        transitions.add(transition);

        State initial_state = new State("s0", transitions, false, true);
        State final_state = new State("s1", new ArrayList<>(), true, false);

        states.add(initial_state);
        states.add(final_state);

        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add(String.valueOf(character));
        nfas.push(new NFA(alphabet, states));
    }



    public void OR(NFA A, NFA B){
        ArrayList<State> states = new ArrayList<>();
        ArrayList<String> alphabet = new ArrayList<>();
        int numberOfStates = A.getStatesNumber() + B.getStatesNumber() + 2;

        int i = 1;
        while (i < numberOfStates){
            ArrayList<Transition> initalStateTransitions = new ArrayList<>();
            initalStateTransitions.add(new Transition("s1", EPSILON));
            State initial_state = new State("s0", initalStateTransitions, false, true);
            initalStateTransitions.add(new Transition("s" + (B.getStates().size() + 1), EPSILON));
            states.add(initial_state);

            for(State state: B.getStates()){
                State s = setNewORStates(state, i, numberOfStates - 1);
                states.add(s);
                i++;
            }

            for(State state: A.getStates()){
                State s = setNewORStates(state, i, numberOfStates - 1);
                states.add(s);
                i++;
            }

            State final_state = new State("s" + (numberOfStates -1), new ArrayList<>(), true, false);
            i++;
            states.add(final_state);
        }

        alphabet.add(String.valueOf(EPSILON));
        alphabet.addAll(B.getAlphabet());
        alphabet.addAll(A.getAlphabet());

        nfas.push(new NFA(alphabet, states));
    }

    private State setNewORStates(State state, int i, int currentFinalStateNewValue) {

        int transitionNumbers = state.getTransitions().size();

        if(transitionNumbers > 0){
            for(Transition transition: state.getTransitions()){
                String transitionPosition = String.valueOf(transition.getKey().charAt(1));
//                if(state.getId().equals("sf")){
//                    transition.setKey("s" + currentFinalStateNewValue);
//                }
                if(transitionPosition.equals("f")){
                    transition.setKey("s" + (i + 1));
                }else {
                    transition.setKey("s" + (Integer.parseInt(transitionPosition) + 1));
                    transition.setValue(transition.getValue());
                }
            }

        }else{
            ArrayList<Transition> transitions = new ArrayList<>();
            Transition transition = new Transition();
            transition.addTransition("s" + currentFinalStateNewValue, EPSILON);
            transitions.add(transition);
            state.setTransitions(transitions);
        }

        state.setFinal(false);
        state.setInitial(false);
        state.setId("s" + i);
        return state;
    }

    public void executeAlgorithm(){
        this.regex = parser.parse();
        baseStep('a');
        baseStep('b');
        //a|b
        //ab | c |
        OR(nfas.pop(), nfas.pop());
        baseStep('c');
        OR(nfas.pop(), nfas.pop());
        System.out.println(nfas.size());
        System.out.println("we did it");
//        for (int i = 0; i < this.regex.length(); i++) {
//            char character = this.regex.charAt(i);
//            baseStep(character);
//        }
    }
}
