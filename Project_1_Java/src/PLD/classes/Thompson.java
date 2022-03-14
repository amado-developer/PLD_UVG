package PLD.classes;

import java.util.ArrayList;
import java.util.Stack;

public class Thompson {
    private Stack<NFA> nfas = new Stack<>();
    private String regex;
    private Lexer lexer;
    private Parser parser;

    public Thompson(String regex) {
        this.regex = regex;
        this.lexer = new Lexer(regex);
        this.parser = new Parser(lexer);
    }

    public void baseStep(char character){
        ArrayList<State> states = new ArrayList<>();
        ArrayList<Transition> transitions = new ArrayList<>();
        Transition transition = new Transition();
        transition.addTransition("sf", character);
        transitions.add(transition);

        State initial_state = new State("s0", transitions, false, true );
        State final_state = new State("s1", null, true, false );
        states.add(initial_state);
        states.add(final_state);

        nfas.push(new NFA(new String[]{String.valueOf(character)}, states, "s0", "sf" ));
    }

    public void executeAlgorithm(){
        this.regex = parser.parse();

        for (int i = 0; i < this.regex.length(); i++) {
            char character = this.regex.charAt(i);
            baseStep(character);
        }
    }
}
