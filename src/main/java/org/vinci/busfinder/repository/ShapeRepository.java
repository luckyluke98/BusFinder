package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.dto.ShapeDto;
import org.vinci.busfinder.model.Shape;

import java.util.List;

@Repository
public interface ShapeRepository extends JpaRepository<Shape, Integer> {

    @Modifying
    @Query(value = "INSERT INTO shapes (shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence,shape_dist_traveled)" +
            "VALUES (?1,?2,?3,?4,?5)", nativeQuery = true)
    void saveRaw(String shapeId, String shapePtLat, String shapePtLon, int shapePtSeq, String shapeDistTraveled);

    default void saveAllRaw(List<ShapeDto> shapes) {
        shapes.forEach(shape -> {
            saveRaw(shape.getShapeId(), shape.getShapePtLat(), shape.getShapePtLon(), shape.getShapePtSeq(),
                    shape.getShapeDistTraveled());
        });
    }

}
