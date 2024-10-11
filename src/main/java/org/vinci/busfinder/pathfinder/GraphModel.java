package org.vinci.busfinder.pathfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GraphModel {

    private HashMap<Integer, HashMap<Integer, List<Integer>>> graph;
    private HashMap<Integer, HashMap<Integer, List<Integer>>> cachedShortestPath;

    public GraphModel() {
        graph = new HashMap<>();
        cachedShortestPath = new HashMap<>();
    }

    public GraphModel(List<Integer> nodes) {
        graph = new HashMap<>();
        cachedShortestPath = new HashMap<>();
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

        if (graph.get(node1).isEmpty()) {
            graph.get(node1).put(node2, new ArrayList<>());
        }

        if (graph.get(node1).containsKey(node2)) {
            if (!graph.get(node1).get(node2).contains(link)) {
                graph.get(node1).get(node2).add(link);
                return true;
            }
        } else {
            graph.get(node1).put(node2, new ArrayList<>());
            graph.get(node1).get(node2).add(link);
            return true;
        }

        return false;
    }

    public List<Integer> shortestPath(int startNode, int endNode) {
        if (isShortestPathCached(startNode)) {
            return getCachedShortestPath(startNode, endNode);
        }

        int numNodes = getMaxValueNode();
        int[] dist = new int[numNodes];

        HashMap<Integer, List<Integer>> paths = new HashMap<>();

        //Initialize for each stop the corresponding shortest path list
        for (int i = 0; i < numNodes; i++) {
            paths.put(i, new ArrayList<>());
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
        cacheShortestPath(startNode, paths);
        return paths.get(endNode);
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

    public void clear() {
        graph.clear();
        cachedShortestPath.clear();
    }

    public HashMap<Integer, List<Integer>> getNode(int node) {
        return graph.get(node);
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

    private boolean areAdjacentNodes(int startNode, int endNode) {
        return graph.containsKey(startNode) && graph.get(startNode).containsKey(endNode);
    }

    private boolean isShortestPathCached(int node) {
        return cachedShortestPath.containsKey(node);
    }

    private List<Integer> getCachedShortestPath(int startNode, int endNode) {
        return cachedShortestPath.get(startNode).get(endNode);
    }

    private void cacheShortestPath(int startNode, HashMap<Integer, List<Integer>> paths) {
        cachedShortestPath.put(startNode, paths);
    }

    public void printGraph(){
        graph.forEach((k,v) -> {
            v.forEach((k1,v1) -> {
                System.out.println(k+": "+k1+": "+v1);
            });
        });
    }

}
