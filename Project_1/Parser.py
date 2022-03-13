class Parser:
    def __init__(self, lexer):
        self.lexer = lexer
        self.tokens = []
        self.token_iterator = self.lexer.generate_token()

    def check(self, identifier):
        if self.token_iterator.id == identifier:
            self.token_iterator = self.lexer.generate_token()
        elif self.token_iterator.id != identifier:
            print('Bad Character!')

    def parse(self):
        print(self.lexer.get_regex_length())
        print(self.token_iterator.id)
        self.check('CHAR')
        print(self.token_iterator.id)
        self.check('ASTERISK')
        print(self.token_iterator.id)
