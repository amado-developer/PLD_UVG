from Token import *


class LexerAnalyser:
    def __init__(self, regex):
        self.regex = regex
        self.operators = ['(', ')', '*', '|', '\x08', '+', '?']
        self.position = 0
        self.regex_length = len(regex)

    def generate_token(self):
        if self.position < self.regex_length:
            char = self.regex[self.position]
            self.position += 1

            if char not in self.operators:
                return Token('CHAR', char)
            elif char == '(':
                return Token('OPEN_PAR', char)
            elif char == ')':
                return Token('CLOSE_PAR', char)
            elif char == '*':
                return Token('ASTERISK', char)
            elif char == '+':
                return Token('PLUS', char)
            elif char == '?':
                return Token('QUESTION_MARK', char)
            elif char == '|':
                return Token('OR', char)
            elif char == '\x08':
                return Token('CON', char)

    def get_alphabet(self):
        alphabet = []
        for char in self.regex:
            if char not in alphabet \
                    and (str(char).isalpha()
                         or str(char).isalnum()):
                alphabet.append(char)
        alphabet.sort()
        return alphabet

    def get_regex_length(self):
        return self.regex_length
