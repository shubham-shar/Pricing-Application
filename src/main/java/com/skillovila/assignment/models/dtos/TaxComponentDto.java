package com.skillovila.assignment.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxComponentDto {
    private Double gstAmount;
}
