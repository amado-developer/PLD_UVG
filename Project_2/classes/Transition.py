class Transition:
    def __init__(self):
        self.key = 0
        self.value = 0

    def __object_init__(self, obj):
        self.key = obj.key
        self.value = obj.value

    def add_transition(self, key, value):
        self.key = key
        self.value = value
