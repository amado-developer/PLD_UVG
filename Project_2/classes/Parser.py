from classes.Token import Token


class Parser:
    def __init__(self, lexer):
        self.lexer = lexer
        self.operators = []
        self.output = []
        self.lookahead = None

    def consume_token(self):
        self.lookahead = self.lexer.get_token()

    def parse(self):
        for i in range(len(self.lexer.regex)):
            self.consume_token()
            if self.lookahead.id == 'OPEN_PAR':
                self.operators.append(self.lookahead)
            elif self.lookahead.id == 'CLOSE_PAR':
                while self.operators and self.operators[-1].id != 'OPEN_PAR':
                    self.output.append(self.operators.pop())
                if self.operators:
                    self.operators.pop()
            elif self.lookahead.id == 'CHAR':
                self.output.append(self.lookahead)
            else:
                while len(self.operators) > 0 and self.lookahead.precedence <= self.operators[-1].precedence \
                        and self.operators[-1].id != 'OPEN_PAR' and self.operators[-1].id != 'CLOSE_PAR':
                    self.output.append(self.operators.pop())
                self.operators.append(self.lookahead)

        operators_size = len(self.operators)
        for i in range(operators_size):
            token = self.operators.pop()
            self.output.append(token)
        self.operators.clear()

        postfix_regex = ''

        for t in self.output:
            postfix_regex += t.value

        return postfix_regex
