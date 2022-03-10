class Parser:
    def __init__(self, regex):
        self.regex = regex
        self.regular_expressions = []
        self.operators = ['*', "?", "|", "+", "(", ")"]

    def parse(self):
        flag = False
        expression = ""
        for pos in range(len(self.regex)):
            char = self.regex[pos]
            if char == "(":
                flag = True
                expression += char

            elif flag:
                expression += char

            if char == ")":
                if pos != len(self.regex) \
                        and self.regex[pos + 1] in self.operators:
                    expression += self.regex[pos + 1]
                flag = False

                self.regular_expressions.append([expression])
                expression = ""

            if char in self.operators and not flag:
                pass
        print(self.regular_expressions)

    def get_alphabet(self):
        alphabet = []
        for char in self.regex:
            if char not in alphabet \
                    and (str(char).isalpha()
                         or str(char).isalnum()):
                alphabet.append(char)
        alphabet.sort()
        return alphabet
