package com.skillovila.assignment.repositories;

import com.skillovila.assignment.models.entities.TaxComponent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
public interface TaxComponentRepository extends JpaRepository<TaxComponent, Long> {}
