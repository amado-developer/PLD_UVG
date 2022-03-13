package PLD.classes;

import java.util.*;

public class Parser {

    private Lexer lexer;
    private Stack<Token> operators = new Stack<>();
    private Queue<Token> output =  new LinkedList<>();
    private Token lookahead;

    public Parser(Lexer lexer){
        this.lexer = lexer;
    }

    /**
     * This functions consumes the next token from our Token Table
     */
    public void consumeToken(){
        this.lookahead = this.lexer.getToken();
    }

    /***
     * Function to convert the Infix Regex notation to a postfix one
     */
    public void parse(){
        for (int i = 0; i < this.lexer.getRegexLength(); i++) {
            consumeToken();
            if(operators.size() > 0){
                if(this.lookahead.getValue() == operators.peek().getValue()){
                    this.output.add(this.lookahead);
                    operators.pop();
                }
            }

            if(this.lookahead.getId().equals("CHAR")){
                output.add(this.lookahead);
            }else if(this.lookahead.getId().equals("CLOSE_PAR")){
                boolean flag = true;
                while (flag){
                    Token t = operators.pop();
                    if(t.getId().equals("OPEN_PAR")){
                        flag = false;
                    }else {
                        this.output.add(t);
                    }
                }
            }else if(this.lookahead.getId().equals("KLEAN") || this.lookahead.getId().equals("PLUS") ){
                this.output.add(this.lookahead);
            }
            else {
                operators.push(this.lookahead);
            }
        }

        for(Token t: operators){
            this.output.add(t);
        }

        this.operators.clear();


        for(Token t: output){
            System.out.print(t.getValue());
        }
    }
}
