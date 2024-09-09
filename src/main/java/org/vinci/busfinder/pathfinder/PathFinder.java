package org.vinci.busfinder.pathfinder;

import jakarta.persistence.Tuple;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class PathFinder {

    private int startStopId;
    private int endStopId;
    private String departureTime;

    private List<Path> paths;
    PriorityQueue<Pair<Integer, List<Integer>>> topTratte;

    private final int HEAP_TOP_DIM = 10;

    @Autowired
    private GraphDataManger gdm;

    public PathFinder() {
        this.paths = new ArrayList<>();
        this.topTratte = new PriorityQueue<>(Comparator.comparingInt(a -> a.a));
    }

    public List<Pair<Integer, List<Integer>>> findAllPaths() {
        Path path = new Path();
        boolean[] visitedStop = new boolean[gdm.getStopNetwork().keySet().stream().max(Integer::compare).get()];

        this.paths.clear();

        findAllPathRec(startStopId, path, visitedStop);

        Collections.sort(paths, Comparator.comparingInt(p -> p.getStops().size()));

        for (Path p : this.paths.subList(0,5)) {
            List<List<Integer>> conns = new ArrayList<>();

            for (int i = 0; i < p.getStops().size(); i++) {
                if (i + 1 < p.getStops().size()) {
                    List<Integer> conn = gdm.getStopNetwork().get(p.getStops().get(i)).get(p.getStops().get(i + 1));
                    if (Objects.nonNull(conn))
                        conns.add(conn);
                }
            }

            findComb(conns);

            break;

        }

        List<Pair<Integer, List<Integer>>> res = new ArrayList<>();
        res.add(topTratte.peek());

        return res;
    }

    private void findComb(List<List<Integer>> conns) {
        List<Integer> comb = new ArrayList<>();

        findCombRec(conns, comb, 0);

    }

    private void findCombRec(List<List<Integer>> conns, List<Integer> comb, int index) {
        if (index >= conns.size()) {
            return;
        }

        for (int i = 0; i < conns.get(index).size(); i++) {
            comb.add(conns.get(index).get(i));

            findCombRec(conns, comb, index + 1);

            if (index == conns.size() - 1) {
                int s = score(comb);
                topTratte.add(new Pair<>(s, new ArrayList<>(comb)));
                System.out.println(s+": "+comb);
                System.out.println(topTratte.size());
            }
            comb.removeLast();
        }
    }

    private Pair<Integer, List<Integer>> findMax() {
        Pair<Integer, List<Integer>> max = topTratte.peek();
        for (Pair<Integer, List<Integer>> p : topTratte) {
            if (max.a < p.a)
                max = p;
        }
        return max;
    }

    private int score(List<Integer> comb) {
        int score = 0;

        for (int i = 0; i < comb.size(); i++) {
            if (i + 1 < comb.size()) {
                if (!Objects.equals(comb.get(i), comb.get(i + 1))) {
                    score ++;
                }
            }
        }
        return score;
    }

    private void findAllPathRec(int curStopId, Path path, boolean[] visitedStop) {

        if (curStopId == endStopId) {
            paths.add(new Path(path));
            return;
        }

        Path path_p = new Path(path);
        visitedStop[curStopId] = true;

        for (Integer adj : gdm.getStopNetwork().get(curStopId).keySet()) {
            if (!visitedStop[adj]) {

                path_p.add(adj);
                findAllPathRec(adj, path_p, visitedStop);
                path_p.removeLast();
            }
        }
        visitedStop[curStopId] = false;
    }

    private String timeAdd(String time1, String time2) {
        String[] t1Split = time1.split(":");
        String[] t2Split = time2.split(":");

        int t1Seconds = Integer.parseInt(t1Split[2]);
        int t2Seconds = Integer.parseInt(t2Split[2]);

        int tMinCurry = (t1Seconds + t2Seconds)/60;
        int tSeconds = (t1Seconds + t2Seconds)%60;

        int t1Minutes = Integer.parseInt(t1Split[1]);
        int t2Minutes = Integer.parseInt(t2Split[1]);

        int tHCurry = (t1Minutes + t2Minutes + tMinCurry)/60;
        int tMin = (t1Minutes + t2Minutes + tMinCurry)%60;

        int t1Hours = Integer.parseInt(t1Split[0]);
        int t2Hours = Integer.parseInt(t2Split[0]);

        int tHour = t1Hours + t2Hours + tHCurry;

        return tHour + ":" + tMin + ":" + tSeconds;

    }

    private int compareTime(String t1, String t2) {
        if(t1.isEmpty() || t2.isEmpty()) {
            throw new RuntimeException("t1 or t2 is empty");
        }
        String[] t1Split = t1.split(":");
        String[] t2Split = t2.split(":");

        if (Integer.parseInt(t1Split[0]) > Integer.parseInt(t2Split[0])) {
            return -1;
        } else if (Integer.parseInt(t1Split[0]) < Integer.parseInt(t2Split[0])) {
            return 1;
        } else {
            if (Integer.parseInt(t1Split[1]) > Integer.parseInt(t2Split[1])) {
                return -1;
            } else if (Integer.parseInt(t1Split[1]) < Integer.parseInt(t2Split[1])) {
                return 1;
            } else {
                if (Integer.parseInt(t1Split[2]) > Integer.parseInt(t2Split[2])) {
                    return -1;
                } else if (Integer.parseInt(t1Split[2]) < Integer.parseInt(t2Split[2])) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    public int getStartStopId() {
        return startStopId;
    }

    public void setStartStopId(int startStopId) {
        this.startStopId = startStopId;
    }

    public int getEndStopId() {
        return endStopId;
    }

    public void setEndStopId(int endStopId) {
        this.endStopId = endStopId;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
