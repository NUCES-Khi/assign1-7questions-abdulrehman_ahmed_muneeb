# Assignment 1 : Question 6
|Std ID|Name|
|------|-|
|K224078|Abdul Rehman Nazeer|
|K228739|Ahmed Muneeb|




**EXPLANATION**
Class Definition (RomaniaMapSearch):

The class encapsulates methods for performing various search algorithms on a map of Romania.
It contains static fields romaniaMap and heuristics to store the graph representing Romania's map and heuristic values for certain cities, respectively.
Initialization:

The static block initializes the romaniaMap and heuristics data structures using predefined values.
Search Algorithms:

**Breadth-First Search (bfs):** Explores nodes level by level, expanding the shallowest unexpanded node first.
**Uniform Cost Search (ucs):** Explores nodes with the least cost first, using a priority queue based on path cost.
**Greedy Best-First Search (greedyBestFirstSearch):** Explores nodes based on a heuristic function, prioritizing nodes closer to the goal.
**Iterative Deepening Depth-First Search (iterativeDeepeningDFS):** Performs depth-limited DFS with increasing depth limits until a solution is found.

**Node Class:**
Represents a node in the search tree.
Stores the city name, cost, heuristic value, and path from the start node.

**Main Method:**
Prompts the user to input the source and destination cities.
Executes each search algorithm with the provided cities and prints the resulting paths.
