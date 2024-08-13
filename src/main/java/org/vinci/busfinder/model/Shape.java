package org.vinci.busfinder.model;

import jakarta.persistence.*;
import org.vinci.busfinder.model.key.ShapeKey;

@Entity
@Table(name = "shapes")
public class Shape {

    @EmbeddedId
    private ShapeKey id;

    @Column(name = "shape_pt_lat")
    private String shapePtLat;

    @Column(name = "shape_pt_lon")
    private String shapePtLon;

    @Column(name = "shape_dist_traveled")
    private String shapeDistTraveled;

    public Shape() {}

    @Override
    public String toString() {
        return String.format(
                "Shape[shape_id=%s, shape_pt_lat=%s, shape_pt_lon=%s, shape_pt_sequence=%s, " +
                        "shape_dist_traveled=%ss]",
                id.getShapeId(), shapePtLat, shapePtLon, id.getShapePtSeq(),shapeDistTraveled
        );
    }

    public ShapeKey getId() {
        return id;
    }

    public void setId(ShapeKey id) {
        this.id = id;
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

    public String getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    public void setShapeDistTraveled(String shapeDistTraveled) {
        this.shapeDistTraveled = shapeDistTraveled;
    }
}
