package org.vinci.busfinder.pathfinder;

import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GraphModel {

    private HashMap<Integer, HashMap<Integer, List<Integer>>> graph;

    public GraphModel() {
        graph = new HashMap<>();
    }

    public GraphModel(List<Integer> nodes) {
        graph = new HashMap<>();
        for (Integer node : nodes) {
            graph.put(node, new HashMap<>());
        }
    }

    public boolean addNode(int node) {
        if (!graph.containsKey(node)) {
            graph.put(node, new HashMap<>());
            return true;
        }
        return false;
    }

    public boolean addEdge(int node1, int node2, int link) {
        if (!graph.containsKey(node1)) {
            return false;
        }

        // Se la lista degli adiacenti di node1 è vuota allora andiamo a popolarla con node2
        if (graph.get(node1).isEmpty()) {
            graph.get(node1).put(node2, new ArrayList<>());
        }

        //Lista degli adiacenti di node1 non è vuota, se ha come adiacente node2 verifichiamo che link
        // non sia gia presente nella lista dei link e lo aggiungiamo
        if (graph.get(node1).containsKey(node2)) {
            if (!graph.get(node1).get(node2).contains(link)) {
                graph.get(node1).get(node2).add(link);
                return true;
            }
        }
        // Se tra gli adiacenti di node1 non c'e' node2, lo inseriamo e quindi anche il link
        else {
            graph.get(node1).put(node2, new ArrayList<>());
            graph.get(node1).get(node2).add(link);
            return true;
        }

        return false;
    }

    public List<Integer> shortestPath(int startNode, int endNode) {
        int numNodes = getMaxValueNode();
        int[] dist = new int[numNodes];

        HashMap<Integer, List<Integer>> paths = new HashMap<>();

        //Initialize for each stop the corresponding shortest path list
        for (int i = 0; i < numNodes; i++) {
            paths.put(i, new ArrayList<>());
            if (i == endNode) {
                paths.get(i).add(startNode);
            }
        }

        Boolean[] sptSet = new Boolean[numNodes];

        for (int n = 0; n < numNodes; n++) {
            dist[n] = Integer.MAX_VALUE;
            sptSet[n] = false;
        }

        dist[startNode] = 0;

        for (int count = 0; count < numNodes; count++) {
            int u = minDistance(dist, sptSet, numNodes);
            sptSet[u] = true;

            for (int n = 0; n < numNodes; n++) {
                if (!sptSet[n] && areAdjacentNodes(u, n)
                        && dist[u] != Integer.MAX_VALUE
                        && dist[u] + 1 < dist[n]) {
                    dist[n] = dist[u] + 1;
                    List<Integer> aux = new ArrayList<>(paths.get(u));
                    aux.add(n);
                    paths.get(n).addAll(aux);
                }
            }
        }
        return paths.get(endNode);
    }

    private int minDistance(int[] dist, Boolean[] sptSet, int numNodes) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < numNodes; v++)
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        return min_index;
    }

    public boolean areAdjacentNodes(int startNode, int endNode) {
        return graph.containsKey(startNode) && graph.get(startNode).containsKey(endNode);
    }

    public int getNumberOfNodes() {
        return graph.size();
    }

    public int getMaxValueNode() {
        return graph.keySet().stream().max(Integer::compare).get() + 1;
    }

    public List<Integer> getNodes() {
        return graph.keySet().stream().toList();
    }

}
