from Parser import *

regex = '(b|b)*abb(a|b)*'
word = 'babbaaaaa'
parser = Parser(regex)
alphabet = parser.get_alphabet()
print(alphabet)
parser.parse()
