package campusgraph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the university campus as an undirected graph.
 * Locations (buildings, landmarks, etc.) are vertices; roads/paths
 * between them are edges.
 *
 * Owned by: Member 4
 * Covers assignment requirements 7, 8, 9, 10:
 *   - Graph representing campus locations and connections
 *   - Adjacency list representation
 *   - Add/remove locations and connections
 *   - Display connected locations / campus network
 *
 * IMPLEMENTATION NOTE:
 * The adjacency list is represented as a Map<String, List<String>>,
 * where each key is a location name and its value is the list of
 * directly connected locations. A LinkedHashMap is used (rather than
 * HashMap) so that locations display in the order they were added,
 * which makes demo output easier to follow.
 *
 * Connections are treated as undirected (a road between A and B can be
 * walked in either direction) — if the team's use case needs one-way
 * routes instead, addConnection()/removeConnection() would only need
 * to update one side of the map.
 *
 * INTEGRATION NOTE:
 * This class is fully independent of Student, the linked list, stack/
 * queue, and BST/hash table modules. GraphTraversal.java (BFS/DFS)
 * operates on top of this class via getNeighbors(), so the two files
 * together form the complete graph module.
 */
public class CampusGraph {

    private final Map<String, List<String>> adjacencyList;

    public CampusGraph() {
        this.adjacencyList = new LinkedHashMap<>();
    }

    /**
     * Adds a new campus location (vertex) to the graph.
     * Satisfies menu item 10: "Add Campus Location".
     *
     * @return true if added, false if the location already exists
     *         (duplicate locations rejected — requirement 14).
     */
    public boolean addLocation(String location) {
        if (location == null || location.isBlank()) {
            return false;
        }
        if (adjacencyList.containsKey(location)) {
            return false; // Duplicate location
        }
        adjacencyList.put(location, new ArrayList<>());
        return true;
    }

    /**
     * Removes a campus location and any connections/roads linked to it.
     * Satisfies menu item 11: "Remove Campus Location".
     *
     * @return true if removed, false if the location does not exist.
     */
    public boolean removeLocation(String location) {
        if (location == null || !adjacencyList.containsKey(location)) {
            return false; // Location not found
        }

        // Remove the location itself
        adjacencyList.remove(location);

        // Remove this location from every other location's neighbour list
        for (List<String> neighbours : adjacencyList.values()) {
            neighbours.remove(location);
        }
        return true;
    }

    /**
     * Adds a two-way connection/road between two existing locations.
     * Satisfies menu item 12: "Add Campus Connection/Road".
     *
     * @return true if the connection was added, false if either
     *         location does not exist, or the connection already
     *         exists (requirement 14: handling unavailable/invalid
     *         connections).
     */
    public boolean addConnection(String locationA, String locationB) {
        if (locationA == null || locationB == null) {
            return false;
        }
        if (!adjacencyList.containsKey(locationA) || !adjacencyList.containsKey(locationB)) {
            return false; // One or both locations don't exist
        }
        if (locationA.equals(locationB)) {
            return false; // No self-connections
        }
        if (adjacencyList.get(locationA).contains(locationB)) {
            return false; // Connection already exists
        }

        adjacencyList.get(locationA).add(locationB);
        adjacencyList.get(locationB).add(locationA);
        return true;
    }

    /**
     * Removes the connection/road between two locations.
     * Satisfies menu item 13: "Remove Campus Connection/Road".
     *
     * @return true if the connection was removed, false if either
     *         location doesn't exist or no connection was present
     *         between them (requirement 14).
     */
    public boolean removeConnection(String locationA, String locationB) {
        if (locationA == null || locationB == null) {
            return false;
        }
        if (!adjacencyList.containsKey(locationA) || !adjacencyList.containsKey(locationB)) {
            return false;
        }
        boolean removedA = adjacencyList.get(locationA).remove(locationB);
        boolean removedB = adjacencyList.get(locationB).remove(locationA);
        return removedA || removedB;
    }

    /**
     * Returns the list of locations directly connected to the given
     * location. Used internally by GraphTraversal for BFS/DFS.
     *
     * @return the list of neighbours, or an empty list if the location
     *         doesn't exist.
     */
    public List<String> getNeighbours(String location) {
        return adjacencyList.getOrDefault(location, new ArrayList<>());
    }

    public boolean hasLocation(String location) {
        return adjacencyList.containsKey(location);
    }

    /**
     * Displays every location and the locations it's directly
     * connected to.
     * Satisfies menu item 14: "Display Campus Connections".
     */
    public void displayConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("No campus locations have been added yet.");
            return;
        }

        System.out.println("---- Campus Network (Adjacency List) ----");
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            String location = entry.getKey();
            List<String> neighbours = entry.getValue();
            if (neighbours.isEmpty()) {
                System.out.println(location + " -> (no connections)");
            } else {
                System.out.println(location + " -> " + String.join(", ", neighbours));
            }
        }
    }

    public int getLocationCount() {
        return adjacencyList.size();
    }

    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }
}
