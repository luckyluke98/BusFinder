package org.vinci.busfinder.dto;

import jakarta.persistence.Column;

public class ShapeDto {

    private String shapeId;
    private String shapePtLat;
    private String shapePtLon;
    private int shapePtSeq;
    private String shapeDistTraveled;

    public ShapeDto() {}

    public ShapeDto(String shapeId, String shapePtLat, String shapePtLon, int shapePtSeq, String shapeDistTraveled) {
        this.shapeId = shapeId;
        this.shapePtLat = shapePtLat;
        this.shapePtLon = shapePtLon;
        this.shapePtSeq = shapePtSeq;
        this.shapeDistTraveled = shapeDistTraveled;
    }

    @Override
    public String toString() {
        return String.format(
                "Shape[shape_id=%s, shape_pt_lat=%s, shape_pt_lon=%s, shape_pt_sequence=%s, " +
                        "shape_dist_traveled=%ss]",
                shapeId, shapePtLat, shapePtLon, shapePtSeq,shapeDistTraveled
        );
    }

    public String getShapeId() {
        return shapeId;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    public String getShapePtLat() {
        return shapePtLat;
    }

    public void setShapePtLat(String shapePtLat) {
        this.shapePtLat = shapePtLat;
    }

    public String getShapePtLon() {
        return shapePtLon;
    }

    public void setShapePtLon(String shapePtLon) {
        this.shapePtLon = shapePtLon;
    }

    public int getShapePtSeq() {
        return shapePtSeq;
    }

    public void setShapePtSeq(int shapePtSeq) {
        this.shapePtSeq = shapePtSeq;
    }

    public String getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    public void setShapeDistTraveled(String shapeDistTraveled) {
        this.shapeDistTraveled = shapeDistTraveled;
    }
}
