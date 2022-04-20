from classes.FA import FA
from classes.Lexer import Lexer
from classes.Parser import Parser
from classes.State import State
from classes.Transition import Transition


class Thompson:
    def __init__(self, regex):
        self.regex = regex
        self.lexer = Lexer(regex)
        self.parser = Parser(self.lexer)
        self.fas = []
        self.EPSILON = '\u03F5'

    def base_step(self, character):
        states = []
        transitions = []
        transition = Transition()

        transition.add_transition("s1", character)
        transitions.append(transition)

        initial_state = State("s0", transitions, False, True)
        final_state = State("s1", [], True, False)

        states.append(initial_state)
        states.append(final_state)

        self.fas.append(FA([], states))

    def __OR(self, A, B):
        states = []
        alphabet = []
        number_of_states = len(A.states) + len(B.states)

        init_transitions = [Transition("s1", self.EPSILON), Transition("s" + (len(B.states)), self.EPSILON)]

        initial_state = State("s0", init_transitions, False, True)
        states.append(initial_state)

        for state in B.states:
            self.__set_OR_states(states, number_of_states, state)

        for state in A.states:
            self.__set_OR_states(states, number_of_states, state, len(B.states))

        final_state = State("s" + (number_of_states + 1), [], True, False)
        states.append(final_state)
        self.fas.append(FA([], states))

    def __set_OR_states(self, states, number_of_states, state, extra_value=0):
        state_number = int(state.id[1])
        state.id = "s" + str((state_number + 1 + extra_value))
        state.is_initial = False
        state.is_final = False

        if len(state.transitions) == 0:
            temp_transitions = []
            temp_transition_1 = Transition()
            temp_transition_1.add_transition("s" + (number_of_states + 1), self.EPSILON)
            temp_transitions.append(temp_transition_1)
            state.transitions = temp_transitions
        else:
            for transition in state.transitions:
                transition_number = int(transition.key[1])
                transition.key = "s" + str((transition_number + 1 + extra_value))

        states.append(state)

    def __KLEAN(self, A):
        states = []
        number_of_states = len(A.states)

        transitions = [Transition("s1", self.EPSILON), Transition("s" + str((number_of_states + 1)), self.EPSILON)]
        initial_state = State("s0", transitions, False, True)

        states.append(initial_state)

        for state in A.states:
            state_number = int(state.id[1])
            state.id = "s" + (state_number + 1)
            state.is_initial = False
            state.is_final = False

            if len(state.transitions) == 0:
                temp_transitions = []
                temp_transition_1 = Transition()
                temp_transition_2 = Transition()
                temp_transition_1.add_transition("s" + str((number_of_states + 1)), self.EPSILON)
                temp_transition_2.add_transition("s1", self.EPSILON)
                temp_transitions.append(temp_transition_1)
                temp_transitions.append(temp_transition_2)
                state.transitions = temp_transitions

            else:
                for transition in state.transitions:
                    transition_number = int(transition.key[1])
                    transition.key = "s" + str((transition_number + 1))

            states.append(state)

        final_state = State("s" + str((number_of_states + 1)), [], True, False)
        states.append(final_state)
        self.fas.append(FA([], states))

    def __CONCAT(self, A, B):
        states = []
        number_of_states = int(len(B.states) - 1)

        a = B.states[len(B.states) - 1]
        b = A.states[0]

        states.append(B.states)
        states.pop(len(states) - 1)

        for state in A.states:
            for transition in state.transitions:
                new_position = int(transition.key[1]) + int(number_of_states)
                transition.key = "s" + str(new_position)

            if state.id == "s0":
                state.id = "s" + str(number_of_states)
            else:
                state.id = "s" + str(int((int(state.id[1]) + int(number_of_states))))

            states.append(state)

        self.fas.append(FA([], states))

    def __PLUS(self, A):
        B = FA(A)
        self.__KLEAN(A)
        self.__CONCAT(self.fas.pop(), B)

    def execute_algorithm(self):
        self.regex = self.parser.parse()

        for i in range(len(self.regex)):
            character = self.regex[i]

            if character == '*':
                self.__KLEAN(self.fas.pop())
            elif character == '+':
                self.__PLUS(self.fas.pop())
            elif character == '?':
                self.base_step(self.EPSILON)
                self.__OR(self.fas.pop(), self.fas.pop())
            elif character == '&':
                self.__CONCAT(self.fas.pop(), self.fas.pop())
            elif character == '|':
                self.__OR(self.fas.pop(), self.fas.pop())
            else:
                self.base_step(character)
        final_sm = self.fas.pop()
        final_sm.graph(final_sm, False)

        return final_sm