package com.skillovila.assignment.repositories;

import java.util.Set;

import com.skillovila.assignment.models.entities.Course;
import com.skillovila.assignment.models.enums.PricingStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = Queries.FETCH_COURSE_BASED_ON_STRATEGY)
    Set<Course> findCoursesByStrategy(@Param("type") PricingStrategy strategy);
    
    class Queries {
    
        private Queries() {}
    
        public static final String FETCH_COURSE_BASED_ON_STRATEGY = "select c from Course c "
                + "join Price p on c.id = p.course.id "
                + "join PricingStrategy ps on ps.price.id = p.id "
                + "where ps.type = :type ";
    }
}
