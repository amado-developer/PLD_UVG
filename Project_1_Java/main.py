import graphviz

def graph():
    afn = graphviz.Digraph('finite_state_machine', filename='AFN.gv')
    afn.attr(rankdir='LR', size='8,5')

    with open("NFA.txt") as NFA:
        lines = NFA.readlines()

    afn.attr('node', shape='doublecircle')
    afn.node('s0')
    afn.node(lines[-1].replace('|', '', 2))

    afn.attr('node', shape='circle')
    lines[-1] = 's' + str(len(lines))
    for line in lines:
        try:
            state, transition, value = line.split("|")
            afn.edge(state, transition, label=value)
        except:
            pass

    afn.view()
    
graph()
