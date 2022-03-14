package PLD.classes;

import java.util.HashMap;
import java.util.Map;

public class Transition {
    private Map<String, Character> transition = new HashMap<>();

    public Map<String, Character> getTransition() {
        return transition;
    }

    public void addTransition(String key, char value){
        this.transition.put(key, value);
    }
}
