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

        transition.addTransition("s1", character);
        transitions.add(transition);

        State initial_state = new State("s0", transitions, false, true);
        State final_state = new State("s1", new ArrayList<>(), true, false);

        states.add(initial_state);
        states.add(final_state);

        ArrayList<String> alphabet = new ArrayList<>();
        alphabet.add(String.valueOf(character));
        nfas.push(new NFA(alphabet, states));
    }

    private void OR(NFA A, NFA B){
        ArrayList<State> states = new ArrayList<>();
        ArrayList<String> alphabet = new ArrayList<>();
        int numberOfStates = A.getStatesNumber() + B.getStatesNumber();

        ArrayList<Transition> initTransitions = new ArrayList<>();
        initTransitions.add(new Transition("s1", EPSILON));
        initTransitions.add(new Transition("s" + (B.getStatesNumber() + 1), EPSILON));
        State initial_state = new State("s0", initTransitions, false, true);
        states.add(initial_state);

        for(State state : B.getStates()) {
            setORStates(states, numberOfStates, state, 0);
        }

        for(State state : A.getStates()) {
            setORStates(states, numberOfStates, state, B.getStates().size());
        }

        State final_state = new State("s" + (numberOfStates + 1), new ArrayList<>(), true, false);
        states.add((final_state));
        nfas.push(new NFA(alphabet, states));
    }

    private void setORStates(ArrayList<State> states, int numberOfStates, State state, int extraValue) {
        int stateNumber = Integer.parseInt(String.valueOf(state.getId().charAt(1)));
        state.setId("s" + (stateNumber + 1 + extraValue));
        state.setInitial(false);
        state.setFinal(false);

        if(state.getTransitions().size() == 0){
            ArrayList<Transition> tempTransitions = new ArrayList<>();
            Transition tempTransition1 = new Transition();
            tempTransition1.addTransition("s" + (numberOfStates + 1), EPSILON);
            tempTransitions.add(tempTransition1);
            state.setTransitions(tempTransitions);
        }else{
            for(Transition transition: state.getTransitions()){
                int transitionNumber = Integer.parseInt(String.valueOf(transition.getKey().charAt(1)));
                transition.setKey("s" + (transitionNumber + 1 + extraValue));
            }
        }
        states.add(state);
    }

    /**
     * Executes the KLEAN operation
     * @param A where A is an NFA
     */
    private void KLEAN(NFA A){
        //TODO
        ArrayList<State> states = new ArrayList<>();
        ArrayList<String> alphabet = new ArrayList<>();
        int numberOfStates = A.getStatesNumber();

        ArrayList<Transition> transitions = new ArrayList<>();
        transitions.add(new Transition("s1", EPSILON));
        transitions.add(new Transition("s" + (numberOfStates + 1), EPSILON));
        State initial_state = new State("s0", transitions, false, true);

        states.add(initial_state);


        for(State state : A.getStates()){
            int stateNumber = Integer.parseInt(String.valueOf(state.getId().charAt(1)));
            state.setId("s" + (stateNumber + 1));
            state.setInitial(false);
            state.setFinal(false);

            if(state.getTransitions().size() == 0){
                ArrayList<Transition> tempTransitions = new ArrayList<>();
                Transition tempTransition1 = new Transition();
                Transition tempTransition2 = new Transition();
                tempTransition1.addTransition("s" + (numberOfStates + 1), EPSILON);
                tempTransition2.addTransition("s1", EPSILON);
                tempTransitions.add(tempTransition1);
                tempTransitions.add(tempTransition2);
                state.setTransitions(tempTransitions);
            }else{
                for(Transition transition: state.getTransitions()){
                    int transitionNumber = Integer.parseInt(String.valueOf(transition.getKey().charAt(1)));
                    transition.setKey("s" + (transitionNumber + 1));
                }
            }

            states.add(state);
        }
        State final_state = new State("s" + (numberOfStates + 1), new ArrayList<>(), true, false);
        states.add((final_state));
        nfas.push(new NFA(alphabet, states));
    }

    /**
     * Executes the KLEAN operation
     * @param A where A is a NFA
     * @param B where B is a NFA
     */
    private void CONCAT(NFA A, NFA B){
        //TODO
        ArrayList<State> states = new ArrayList<>();
        ArrayList<String> alphabet = new ArrayList<>();
        int numberOfStates = B.getStatesNumber() - 1;

        State a = B.getStates().get(B.getStates().size() - 1);
        State b = A.getStates().get(0);
        a.setFinal(false);
        b.setInitial(false);

        states.addAll(B.getStates());
        states.remove(states.size() - 1);

        for(State state: A.getStates()){
            for(Transition transition: state.getTransitions()){
                int newPosition = Integer.parseInt(String.valueOf(transition.getKey().charAt(1))) + numberOfStates;
                transition.setKey("s" + newPosition);
            }

            if(state.getId().equals("s0")){
                state.setId("s" + numberOfStates);
            }else {
                state.setId("s" + (Integer.parseInt(String.valueOf(state.getId().charAt(1))) + numberOfStates));
            }


            states.add(state);
        }
        nfas.push(new NFA(alphabet, states));
    }

    private void PLUS(NFA A){
        KLEAN(A);
        NFA kleanNFA = this.nfas.pop();
        baseStep('a');
        CONCAT(kleanNFA, this.nfas.pop());
    }

    public void executeAlgorithm(){
        this.regex = parser.parse();
        baseStep('a');
        NFA a = nfas.pop();
//        baseStep('b');
        PLUS(a);
//        CONCAT(this.nfas.pop(), this.nfas.pop());
//        OR(nfas.pop(), nfas.pop());
//        baseStep('a');
//        CONCAT(this.nfas.pop(), this.nfas.pop());
        //a|b
        //ab | c |
//        OR(nfas.pop(), nfas.pop());
//        baseStep('c');
//        OR(nfas.pop(), nfas.pop());
//        KLEAN(this.nfas.pop());
        System.out.println(nfas.size());
        System.out.println("we did it");
//        System.out.printf(regex);
//        for (int i = 0; i < this.regex.length(); i++) {
//            char character = this.regex.charAt(i);
//            baseStep(character);
//        }
    }
}
