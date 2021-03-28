package com.skillovila.assignment.repositories;

import java.util.Set;

import com.skillovila.assignment.models.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
public interface PriceRepository extends JpaRepository<Price, Long> {
    Set<Price> findByCourseId(Long courseId);
}
