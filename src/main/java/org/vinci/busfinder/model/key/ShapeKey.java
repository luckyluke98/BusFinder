package org.vinci.busfinder.model.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class ShapeKey implements Serializable {

    @Column(name = "shape_id")
    private String shapeId;

    @Column(name = "shape_pt_sequence")
    private int shapePtSeq;

    public ShapeKey() {}

    public String getShapeId() {
        return shapeId;
    }

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    public int getShapePtSeq() {
        return shapePtSeq;
    }

    public void setShapePtSeq(int shapePtSeq) {
        this.shapePtSeq = shapePtSeq;
    }
}
