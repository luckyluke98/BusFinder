package org.vinci.busfinder.pathfinder;

import org.antlr.v4.runtime.misc.Pair;
import org.vinci.busfinder.model.Route;
import org.vinci.busfinder.model.StopTimes;

import java.util.ArrayList;
import java.util.List;

public class TripPath {

    public static class Step {
        private Pair<Route, StopTimesPair> step;

        public Step(Route route, StopTimesPair stopTimesPair) {
            step = new Pair<>(route, stopTimesPair);
        }

        public Pair<Route, StopTimesPair> getStep() {
            return step;
        }

        public void setStep(Pair<Route, StopTimesPair> step) {
            this.step = step;
        }

        public Route getRoute() {
            return step.a;
        }

        public StopTimesPair getStopTimesPair() {
            return step.b;
        }

    }

    public static class StopTimesPair {
        private StopTimes src;
        private StopTimes dst;

        public StopTimesPair(StopTimes src, StopTimes dst) {
            this.src = src;
            this.dst = dst;
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
    }

    private List<Step> steps;

    public TripPath() {
        steps = new ArrayList<>();
    }

    public void addStep(Route route, StopTimes src, StopTimes dst) {
        steps.add(new Step(route, new StopTimesPair(src, dst)));
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public List<Step> getSteps() {
        return steps;
    }


}
