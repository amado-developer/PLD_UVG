class State:
    def __init__(self, id, transitions, is_final, is_initial):
        self.id = id
        self.transitions = transitions
        self.is_final = is_final
        self.is_initial = is_initial

