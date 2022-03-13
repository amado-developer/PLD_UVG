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
    public void parse() {
        int openFlags = 0;
        for (int i = 0; i < this.lexer.getRegexLength(); i++) {
            consumeToken();

            if(this.operators.size() == 0){
                if (this.lookahead.getId().equals("CHAR")) {
                    this.output.add(this.lookahead);
                }else{
                    this.operators.push(this.lookahead);
                }
            } else {
                if (this.lookahead.getId().equals("CHAR")) {
                    this.output.add(this.lookahead);
                } else if (this.lookahead.getId().equals("OPEN_PAR")) {
                    openFlags++;

                    this.operators.push(this.lookahead);

                } else if(openFlags > 0){
                    if (this.lookahead.getId().equals("CLOSE_PAR")) {
                        boolean flag = true;
                        openFlags--;
                        while (flag) {
                            Token t = operators.pop();
                            if (t.getId().equals("OPEN_PAR")) {
                                flag = false;
                            } else {
                                this.output.add(t);
                            }
                        }
                    }else {
                        if(this.operators.peek().getPrecedence() == this.lookahead.getPrecedence()){
                            this.output.add(this.lookahead);
                        }else{
                            this.operators.push(this.lookahead);
                        }
                    }
                }else{
                    if((this.operators.peek().getPrecedence() >= this.lookahead.getPrecedence())) {
                        Token t = this.operators.pop();
                        this.output.add(t);
                        if(this.operators.size() > 0 &&
                                this.operators.peek().getPrecedence() == this.lookahead.getPrecedence()){
                            this.output.add(this.lookahead);
                        }else{
                            this.operators.push(this.lookahead);
                        }

                    } else {
                        this.operators.push(this.lookahead);
                    }
                }
            }
        }

        for (Token t : operators) {
            this.output.add(t);
        }

        this.operators.clear();


        for (Token t : output) {
            System.out.print(t.getValue());
        }
    }
}
