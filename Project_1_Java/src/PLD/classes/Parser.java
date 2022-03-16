package PLD.classes;

import java.util.*;

public class Parser {

    private Lexer lexer;
    private Stack<Token> operators = new Stack<>();
    private Queue<Token> output = new LinkedList<>();
    private Token lookahead;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    /**
     * This functions consumes the next token from our Token Table
     */
    public void consumeToken() {
        this.lookahead = this.lexer.getToken();
    }

    /***
     * Function to convert the Infix Regex notation to a postfix one
     */
    public String parse() {
        for (int i = 0; i < this.lexer.getRegexLength(); i++) {
            consumeToken();
            if(this.lookahead.getId().equals("OPEN_PAR")){
                this.operators.push(this.lookahead);
            }
            else if(this.lookahead.getId().equals("CLOSE_PAR")){
                while (!this.operators.peek().getId().equals("OPEN_PAR")){
                    this.output.add(this.operators.pop());
                }
                this.operators.pop();
            }else if(this.lookahead.getId().equals("CHAR")){
                this.output.add(this.lookahead);
            }else {
                while (this.operators.size() > 0 && this.lookahead.getPrecedence() <= this.operators.peek().getPrecedence()
                && !this.operators.peek().getId().equals("OPEN_PAR") && !this.operators.peek().getId().equals("CLOSE_PAR")){
                    this.output.add(this.operators.pop());
                }
                this.operators.push(this.lookahead);
            }
        }

        int operatorsSize = this.operators.size();
        for (int i = 0; i < operatorsSize; i++) {
            Token token = this.operators.pop();
            this.output.add(token);
        }

        this.operators.clear();

        StringBuilder postfixRegex = new StringBuilder();

        for (Token t : output) {
            postfixRegex.append(t.getValue());
        }

        return postfixRegex.toString();
    }
}
