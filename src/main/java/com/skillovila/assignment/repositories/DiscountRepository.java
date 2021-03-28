package com.skillovila.assignment.repositories;

import java.util.Optional;

import com.skillovila.assignment.models.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByCourseIdAndCouponId(Long courseId, Long couponId);
}
