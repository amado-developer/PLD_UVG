from classes.Token import Token


class Lexer:
    def __init__(self, regex):
        self.regex = regex
        self.operators = {'(': ["OPEN_PAR", 6], ')': ["CLOSE_PAR", 6], '*': ["KLEAN", 5], '+': ["PLUS"],
                          '?': "QUESTION_MARK",  '&': 'CONCAT', '|': "OR"}
        self.position = 0

    def get_token(self):
        if self.position < len(self.regex):
            character = self.regex[self.position]
            self.position += 1
            if self.operators[character]:
                token = self.operators[character]
                return Token(token[0], character, token[1])
            return Token("CHAR", character, 0)

    @staticmethod
    def set_alphabet(regex):
        alphabet = []

        for character in regex:
            if character not in alphabet and Lexer.__is_char(character):
                alphabet.append(character)

        alphabet.sort()
        return alphabet

    @staticmethod
    def __is_char(character):
        if character in ['|', '&', '?', '+', '(', ')', '*']:
            return False
        return True

    @staticmethod
    def pre_process_string(regex):
        new_regex = ''
        for i in range(len(regex)):
            print(regex[i])
            new_regex += regex[i]
            if i < len(regex) - 1:
                if regex[i] == ')' and Lexer.__is_char(regex[i + 1]):
                    new_regex += '&'
                elif regex[i] in ['*', '?', '+'] and Lexer.__is_char(regex[i + 1]):
                    new_regex += '&'
                elif Lexer.__is_char(regex[i]) and regex[i + 1] == '(':
                    new_regex += '&'
                elif Lexer.__is_char(regex[i]) and Lexer.__is_char(regex[i + 1]):
                    new_regex += '&'

        return new_regex




