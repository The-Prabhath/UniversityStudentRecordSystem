package campusgraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides graph traversal algorithms (BFS and DFS) over a CampusGraph.
 *
 * Owned by: Member 4
 * Covers assignment requirement 11: "Implement at least one graph
 * traversal: BFS or DFS." Both are implemented here so the team can
 * offer either (or both) from the menu.
 * Satisfies menu item 15: "Traverse Campus Locations using BFS or DFS".
 *
 * IMPLEMENTATION NOTE:
 * Kept as a separate class from CampusGraph (rather than adding these
 * methods directly to it) so the graph's data structure and its
 * traversal algorithms are cleanly separated — CampusGraph only knows
 * how to store/modify locations and connections, while this class only
 * knows how to walk over them.
 */
public class GraphTraversal {

    /**
     * Performs a Breadth-First Search starting from the given location,
     * visiting all locations reachable from it, level by level.
     *
     * @return the list of locations in the order they were visited, or
     *         an empty list if the starting location doesn't exist
     *         (requirement 14: handling an invalid starting point).
     */
    public List<String> bfs(CampusGraph graph, String startLocation) {
        List<String> visitOrder = new ArrayList<>();

        if (graph == null || startLocation == null || !graph.hasLocation(startLocation)) {
            return visitOrder; // Nothing to traverse
        }

        Set<String> visited = new HashSet<>();
        Deque<String> queue = new ArrayDeque<>();

        queue.addLast(startLocation);
        visited.add(startLocation);

        while (!queue.isEmpty()) {
            String current = queue.removeFirst();
            visitOrder.add(current);

            for (String neighbour : graph.getNeighbours(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.addLast(neighbour);
                }
            }
        }

        return visitOrder;
    }

    /**
     * Performs a Depth-First Search starting from the given location,
     * exploring as far as possible along each branch before backtracking.
     * Implemented iteratively with an explicit stack (rather than
     * recursion) to avoid stack-overflow risk on a large campus network.
     *
     * @return the list of locations in the order they were visited, or
     *         an empty list if the starting location doesn't exist.
     */
    public List<String> dfs(CampusGraph graph, String startLocation) {
        List<String> visitOrder = new ArrayList<>();

        if (graph == null || startLocation == null || !graph.hasLocation(startLocation)) {
            return visitOrder; // Nothing to traverse
        }

        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();

        stack.push(startLocation);

        while (!stack.isEmpty()) {
            String current = stack.pop();

            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            visitOrder.add(current);

            // Push neighbours in reverse so they're explored in the
            // same left-to-right order they were added in the graph
            List<String> neighbours = graph.getNeighbours(current);
            for (int i = neighbours.size() - 1; i >= 0; i--) {
                String neighbour = neighbours.get(i);
                if (!visited.contains(neighbour)) {
                    stack.push(neighbour);
                }
            }
        }

        return visitOrder;
    }

    /**
     * Runs a traversal and prints the result directly to the console.
     * Convenience method for wiring straight into the menu (item 15).
     *
     * @param useBFS true to run BFS, false to run DFS
     */
    public void displayTraversal(CampusGraph graph, String startLocation, boolean useBFS) {
        List<String> result = useBFS ? bfs(graph, startLocation) : dfs(graph, startLocation);

        if (result.isEmpty()) {
            System.out.println("Cannot traverse: location \"" + startLocation + "\" was not found.");
            return;
        }

        System.out.println("---- " + (useBFS ? "BFS" : "DFS") + " Traversal from \"" + startLocation + "\" ----");
        System.out.println(String.join(" -> ", result));
        System.out.println("Locations visited: " + result.size());
    }
}
