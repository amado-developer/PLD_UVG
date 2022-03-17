import graphviz


def graph():
    afn = graphviz.Digraph('finite_state_machine', filename='FA.gv')
    afn.attr(rankdir='LR', size='8,5')

    with open("FA.txt") as NFA:
        lines = NFA.readlines()

    acceptance = []

    afn.attr('node', shape='doublecircle')

    afn.node('s0')
    for line in lines:
        try:
            state, transition, value, is_accepted = line.split("|")
            if is_accepted.strip() == 'true':
                afn.attr('node', shape='doublecircle')
                afn.edge(state, transition, label=value)
                acceptance.append(state)
        except:
            pass

    for line in lines:
        try:
            state, transition, value, is_accepted = line.split("|")
            if is_accepted.strip() == 'false' and state not in acceptance:
                afn.attr('node', shape='circle')
                afn.edge(state, transition, label=value)
        except:
            pass
    print(acceptance)

    afn.view()


graph()
