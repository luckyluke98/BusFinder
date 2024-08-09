package org.vinci.busfinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vinci.busfinder.model.Shape;


@Repository
public interface ShapeRepository extends JpaRepository<Shape, Integer> {
}
