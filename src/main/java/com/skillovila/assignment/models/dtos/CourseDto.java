package com.skillovila.assignment.models.dtos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {
    private Long id;
    private String name;
    private String description;
    private Set<PriceDto> prices;
}
