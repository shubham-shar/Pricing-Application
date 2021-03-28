package com.skillovila.assignment.repositories;

import com.skillovila.assignment.models.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
public interface CouponRepository extends JpaRepository<Coupon, Long> {}
