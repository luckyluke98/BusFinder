package org.vinci.busfinder.model;

import jakarta.persistence.*;

@Entity
@Table(name = "shapes")
public class Shape {

    @Id
    @Column(name = "shape_id")
    private String shapeId;

    @Column(name = "shape_pt_lat")
    private String shapePtLat;

    @Column(name = "shape_pt_lon")
    private String shapePtLon;

    @Column(name = "shape_pt_sequence")
    private int shapePtSeq;

    @Column(name = "shape_dist_traveled")
    private String shapeDistTraveled;

    public Shape() {}

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
