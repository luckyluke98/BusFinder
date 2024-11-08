package org.vinci.busfinder.pathfinder;

import org.vinci.busfinder.model.Route;
import org.vinci.busfinder.model.StopTimes;

import java.util.ArrayList;
import java.util.List;

public class TripPath {

    public static class Step {
        private Route route;
        private StopTimesPair stopTimesPair;

        public Step(Route route, StopTimesPair stopTimesPair) {

            this.route = route;
            this.stopTimesPair = stopTimesPair;
        }

        public Route getRoute() {
            return route;
        }

        public StopTimesPair getStopTimesPair() {
            return stopTimesPair;
        }

        public void setRoute(Route route) {
            this.route = route;
        }

        public void setStopTimesPair(StopTimesPair stopTimesPair) {
            this.stopTimesPair = stopTimesPair;
        }

    }

    public static class StopTimesPair {
        private StopTimes src;
        private StopTimes dst;

        private String srcName;
        private String dstName;

        public StopTimesPair(StopTimes src, StopTimes dst, String srcName, String dstName) {
            this.src = src;
            this.dst = dst;
            this.srcName = srcName;
            this.dstName = dstName;
        }

        public StopTimes getSrc() {
            return src;
        }

        public void setSrc(StopTimes src) {
            this.src = src;
        }

        public StopTimes getDst() {
            return dst;
        }

        public void setDst(StopTimes dst) {
            this.dst = dst;
        }

        public String getSrcName() {
            return srcName;
        }

        public String getDstName() {
            return dstName;
        }

        public void setSrcName(String srcName) {
            this.srcName = srcName;
        }

        public void setDstName(String dstName) {
            this.dstName = dstName;
        }
    }

    private List<Step> steps;

    public TripPath() {
        steps = new ArrayList<>();
    }

    public void addStep(Route route, StopTimes src, StopTimes dst, String srcName, String dstName) {
        steps.add(new Step(route, new StopTimesPair(src, dst, srcName, dstName)));
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public List<Step> getSteps() {
        return steps;
    }


}
