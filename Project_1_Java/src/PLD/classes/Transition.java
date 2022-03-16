package PLD.classes;

public class Transition {
    private Character value;
    private String key;

    public Transition(){

    }

    public Transition(String key, char value){
        this.key = key;
        this.value = value;
    }

    public Transition(Transition obj){
        this.key = obj.key;
        this.value = obj.value;
    }

    public void addTransition(String key, char value){
        this.key = key;
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(Character value) {
        this.value = value;
    }

    public Character getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
