def heavy_SCC():
    # partition = strongly_connected_components(heavy_SCC_graph)
    f = open("ouput.txt", 'w')
    cur_sol = ""

    for k in range(1, 601):
        horse_values, cur_graph = Solver(k);
        scc_list = strongly_connected_components(cur_graph) # get a list of SCCs from the current graph 
        max_val_sum = 0

        cur_sol = cur_sol + " ["
        last_scc = False

        for i in range(len(scc_list)):
            scc = scc_list[i]
            if i == len(scc_list) - 1:
                last_scc = True

            multiplier = len(scc)
            cur_val_sum = 0

            j = 0
            while j < len(scc):
                cur_val_sum += horse_values[scc[j]]
                cur_sol = cur_sol + str(scc[j]) + " "
                if j == len(scc) - 1 and not last_scc:
                    cur_sol += ", "
                j += 1
            cur_val_sum *= multiplier
            max_val_sum += cur_val_sum

        if last_scc:
            cur_sol = cur_sol + "] " + str(max_val_sum) + "\n"
    
    f.write(cur_sol)
    f.close()

def strongly_connected_components(graph):
    """
    # Tarjan's Algorithm (named for its discoverer, Robert Tarjan) is a graph theory algorithm
    # for finding the strongly connected components of a graph.
    
    # Based on: http://en.wikipedia.org/wiki/Tarjan%27s_strongly_connected_components_algorithm
    """

    index_counter = [0]
    stack = []
    lowlinks = {}
    index = {}
    result = []
    
    def strongconnect(node):
        # set the depth index for this node to the smallest unused index
        index[node] = index_counter[0]
        lowlinks[node] = index_counter[0]
        index_counter[0] += 1
        stack.append(node)
    
        # Consider successors of `node`
        try:
            successors = graph[node]
        except:
            successors = []
        for successor in successors:
            if successor not in lowlinks:
                # Successor has not yet been visited; recurse on it
                strongconnect(successor)
                lowlinks[node] = min(lowlinks[node],lowlinks[successor])
            elif successor in stack:
                # the successor is in the stack and hence in the current strongly connected component (SCC)
                lowlinks[node] = min(lowlinks[node],index[successor])
        
        # If `node` is a root node, pop the stack and generate an SCC
        if lowlinks[node] == index[node]:
            connected_component = []
            
            while True:
                successor = stack.pop()
                connected_component.append(successor)
                if successor == node: break
            component = tuple(connected_component)
            # storing the result
            result.append(component)
    
    for node in graph:
        if node not in lowlinks:
            strongconnect(node)
    
    return result 

def Solver(filenumber): 
    f = open("cs170_final_inputs/" + str(filenumber) + ".in" , 'r')
    table =[]
    horses_values = []
    edge = dict()
    num_horses = int(f.readline())
    # random_order = range(num_horses)
    # random.shuffle(random_order)
    visited = []

    for line in f:             #converting input file to 2d array
        line = line.rstrip().split(" ")
        temp = []
        if line != ['']:
            for i in line:
                temp.append(int(i))
            table.append(temp)

    for i in range(num_horses):    #from 2d array, retrieve horses' values and friends list
        for j in range(num_horses):
            val = table[i][j]
            if i == j:
                horses_values.append(val)
            else:
                if val == 1:
                    if i not in edge:
                        edge.setdefault(i, [])
                    edge[i].append(j)
                                        #modify for edges =< num of horse 

    return horses_values, edge

heavy_SCC()
# values, graph = Solver(226)
# graph1 = 0
# scc = strongly_connected_components(graph)
# print graph
# print values
# print scc
# 