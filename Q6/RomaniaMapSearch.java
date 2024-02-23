import java.util.*;

public class RomaniaMapSearch {

    static Map<String, Map<String, Integer>> romaniaMap = new HashMap<>();
    static Map<String, Integer> heuristics = new HashMap<>();

    static {
        initializeRomaniaMap();
        initializeHeuristics();
    }

    static void initializeRomaniaMap() {
        romaniaMap.put("Arad", Map.of("Zerind", 75, "Sibiu", 140, "Timisoara", 118));
        romaniaMap.put("Zerind", Map.of("Arad", 75, "Oradea", 71));
        romaniaMap.put("Oradea", Map.of("Zerind", 71, "Sibiu", 151));
        romaniaMap.put("Sibiu", Map.of("Oradea", 151, "Arad", 140, "Fagaras", 99, "Rimnicu Vilcea", 80));
        romaniaMap.put("Timisoara", Map.of("Arad", 118, "Lugoj", 111));
        romaniaMap.put("Lugoj", Map.of("Timisoara", 111, "Mehadia", 70));
        romaniaMap.put("Mehadia", Map.of("Lugoj", 70, "Drobeta", 75));
        romaniaMap.put("Drobeta", Map.of("Mehadia", 75, "Craiova", 120));
        romaniaMap.put("Craiova", Map.of("Drobeta", 120, "Rimnicu Vilcea", 146, "Pitesti", 138));
        romaniaMap.put("Rimnicu Vilcea", Map.of("Sibiu", 80, "Craiova", 146, "Pitesti", 97));
        romaniaMap.put("Fagaras", Map.of("Sibiu", 99, "Bucharest", 211));
        romaniaMap.put("Pitesti", Map.of("Rimnicu Vilcea", 97, "Craiova", 138, "Bucharest", 101));
        romaniaMap.put("Bucharest", Map.of("Fagaras", 211, "Pitesti", 101, "Giurgiu", 90, "Urziceni", 85));
        romaniaMap.put("Giurgiu", Map.of("Bucharest", 90));
        romaniaMap.put("Urziceni", Map.of("Bucharest", 85, "Hirsova", 98, "Vaslui", 142));
        romaniaMap.put("Hirsova", Map.of("Urziceni", 98, "Eforie", 86));
        romaniaMap.put("Eforie", Map.of("Hirsova", 86));
        romaniaMap.put("Vaslui", Map.of("Urziceni", 142, "Iasi", 92));
        romaniaMap.put("Iasi", Map.of("Vaslui", 92, "Neamt", 87));
        romaniaMap.put("Neamt", Map.of("Iasi", 87));
    }

    static void initializeHeuristics() {
        heuristics.put("Arad", 366);
        heuristics.put("Bucharest", 0);
        heuristics.put("Craiova", 160);
        heuristics.put("Drobeta", 242);
        heuristics.put("Eforie", 161);
        heuristics.put("Fagaras", 176);
        heuristics.put("Giurgiu", 77);
        heuristics.put("Hirsova", 151);
        heuristics.put("Iasi", 226);
        heuristics.put("Lugoj", 244);
        heuristics.put("Mehadia", 241);
        heuristics.put("Neamt", 234);
        heuristics.put("Oradea", 380);
        heuristics.put("Pitesti", 100);
        heuristics.put("Rimnicu Vilcea", 193);
        heuristics.put("Sibiu", 253);
        heuristics.put("Timisoara", 329);
        heuristics.put("Urziceni", 80);
        heuristics.put("Vaslui", 199);
        heuristics.put("Zerind", 374);
    }

    static List<String> bfs(Map<String, Map<String, Integer>> graph, String start, String goal) {
        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(new ArrayList<>(List.of(start)));

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String current = path.get(path.size() - 1);
            if (current.equals(goal)) {
                return path;
            }
            if (!visited.contains(current)) {
                visited.add(current);
                for (String neighbor : graph.get(current).keySet()) {
                    if (!visited.contains(neighbor)) {
                        List<String> newPath = new ArrayList<>(path);
                        newPath.add(neighbor);
                        queue.offer(newPath);
                    }
                }
            }
        }
        return null;
    }

    static Map.Entry<Integer, List<String>> ucs(Map<String, Map<String, Integer>> graph, String start, String goal) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Set<String> visited = new HashSet<>();
        queue.offer(new Node(start, 0, new ArrayList<>(List.of(start))));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            String current = node.name;
            if (current.equals(goal)) {
                return new AbstractMap.SimpleEntry<>(node.cost, node.path);
            }
            if (!visited.contains(current)) {
                visited.add(current);
                for (Map.Entry<String, Integer> neighbor : graph.get(current).entrySet()) {
                    String next = neighbor.getKey();
                    int cost = neighbor.getValue();
                    if (!visited.contains(next)) {
                        List<String> newPath = new ArrayList<>(node.path);
                        newPath.add(next);
                        queue.offer(new Node(next, node.cost + cost, newPath));
                    }
                }
            }
        }
        return null;
    }

    static List<String> greedyBestFirstSearch(Map<String, Map<String, Integer>> graph, Map<String, Integer> heuristics, String start, String goal) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> n.heuristic));
        Set<String> visited = new HashSet<>();
        queue.offer(new Node(start, heuristics.get(start), new ArrayList<>(List.of(start))));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            String current = node.name;
            if (current.equals(goal)) {
                return node.path;
            }
            if (!visited.contains(current)) {
                visited.add(current);
                for (String neighbor : graph.get(current).keySet()) {
                    if (!visited.contains(neighbor)) {
                        List<String> newPath = new ArrayList<>(node.path);
                        // Continuation of the code from the previous snippet

                        newPath.add(neighbor);
                        int heuristic = heuristics.getOrDefault(neighbor, Integer.MAX_VALUE);
                        queue.offer(new Node(neighbor, heuristic, newPath));
                    }
                }
            }
        }
        return null;
    }

    static List<String> iterativeDeepeningDFS(Map<String, Map<String, Integer>> graph, String start, String goal) {
        int maxDepth = 0;
        while (true) {
            List<String> result = depthLimitedDFS(graph, start, goal, maxDepth);
            if (result != null) {
                return result;
            }
            maxDepth++;
        }
    }

    static List<String> depthLimitedDFS(Map<String, Map<String, Integer>> graph, String current, String goal, int maxDepth) {
        return depthLimitedDFSHelper(graph, current, goal, maxDepth, new HashSet<>(), new ArrayList<>(List.of(current)));
    }

    static List<String> depthLimitedDFSHelper(Map<String, Map<String, Integer>> graph, String current, String goal, int maxDepth, Set<String> visited, List<String> path) {
        if (current.equals(goal)) {
            return path;
        }
        if (maxDepth == 0) {
            return null;
        }
        visited.add(current);
        for (String neighbor : graph.get(current).keySet()) {
            if (!visited.contains(neighbor)) {
                List<String> newPath = new ArrayList<>(path);
                newPath.add(neighbor);
                List<String> result = depthLimitedDFSHelper(graph, neighbor, goal, maxDepth - 1, visited, newPath);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    static class Node {
        String name;
        int cost;
        int heuristic;
        List<String> path;

        Node(String name, int cost, List<String> path) {
            this.name = name;
            this.cost = cost;
            this.path = path;
            this.heuristic = 0;
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to Romania Map Search!");
        System.out.print("Enter the source city: ");
        String source = s.nextLine().trim();
        System.out.print("Enter the destination city: ");
        String destination = s.nextLine().trim();

        List<String> bfsPath = bfs(romaniaMap, source, destination);
        System.out.println("BFS Path: " + bfsPath);

        Map.Entry<Integer, List<String>> ucsEntry = ucs(romaniaMap, source, destination);
        System.out.println("UCS Path Cost: " + ucsEntry.getKey());
        System.out.println("UCS Path: " + ucsEntry.getValue());

        List<String> gbfsPath = greedyBestFirstSearch(romaniaMap, heuristics, source, destination);
        System.out.println("GBFS Path: " + gbfsPath);

        List<String> iddfsPath = iterativeDeepeningDFS(romaniaMap, source, destination);
        System.out.println("IDDFS Path: " + iddfsPath);

        s.close();
    }
}