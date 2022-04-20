class Transition:
    def __init__(self, key=0, value=0, obj=None):
        self.key = key
        self.value = value

        if obj:
            self.key = obj.key
            self.value = obj.value

    def add_transition(self, key, value):
        self.key = key
        self.value = value
