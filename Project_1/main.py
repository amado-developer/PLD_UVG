from LexerAnalyser import LexerAnalyser
from Parser import *

# regex = '(b|b)*abb(a|b)*'
# regex = 'a*abb | b'
# word = 'babbaaaaa'
# parser = Parser(regex)
# alphabet = parser.get_alphabet()
# print(alphabet)
# parser.parse()
lex = LexerAnalyser('a*((|')

par = Parser(lex)
par.parse()
