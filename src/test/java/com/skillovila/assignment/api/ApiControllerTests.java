package com.skillovila.assignment.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

import com.skillovila.assignment.models.dtos.CourseDto;
import com.skillovila.assignment.services.PriceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author shubham sharma
 *         <p>
 *         28/03/21
 */
@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(ApiController.class)
public class ApiControllerTests {
    
    public static final String CURRENCY = "currency";
    public static final String INDIAN_CURRENCY = "inr";
    
    @InjectMocks
    ApiController apiController;
    
    @Autowired
    MockMvc mockMvc;
    
    @Mock
    private PriceService priceService;
    
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }
    
    
    @Test
    public void getAllCourses() throws Exception {
        Mockito.when(priceService.getCourses(anyString()))
            .thenReturn(getCourses());
        this.mockMvc.perform(get("/course/all").param(CURRENCY, INDIAN_CURRENCY))
                    .andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void getAllCoursesBadRequest() throws Exception {
        Mockito.when(priceService.getCourses(anyString()))
               .thenReturn(getCourses());
        this.mockMvc.perform(get("/course/all"))
                    .andDo(print()).andExpect(status().is4xxClientError());
    }
    
    @Test
    public void getCourseById() throws Exception {
        Mockito.when(priceService.fetchCourseById(ArgumentMatchers.anyLong(), anyString()))
               .thenReturn(getCourseDto());
        this.mockMvc.perform(get("/course/1/price").param(CURRENCY, INDIAN_CURRENCY))
                    .andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void getCourseByIdBadRequest() throws Exception {
        Mockito.when(priceService.fetchCourseById(ArgumentMatchers.anyLong(), anyString()))
               .thenReturn(getCourseDto());
        this.mockMvc.perform(get("/course/1/price"))
                    .andDo(print()).andExpect(status().is4xxClientError());
    }
    
    @Test
    public void getCourseByStrategy() throws Exception {
        Mockito.when(priceService.fetchCourseById(ArgumentMatchers.anyLong(), anyString()))
               .thenReturn(getCourseDto());
        this.mockMvc.perform(get("/course/strategy")
                                     .param(CURRENCY, INDIAN_CURRENCY).param("type", "ONE_TIME"))
                    .andDo(print()).andExpect(status().isOk());
    }
    
    @Test
    public void getCourseByStrategyBadRequest() throws Exception {
        Mockito.when(priceService.fetchCourseById(ArgumentMatchers.anyLong(), anyString()))
               .thenReturn(getCourseDto());
        this.mockMvc.perform(get("/course/strategy")
                                     .param(CURRENCY, INDIAN_CURRENCY).param("type", "ONE_TIME"))
                    .andDo(print()).andExpect(status().is4xxClientError());
    }
    
    private Set<CourseDto> getCourses() {
        CourseDto course = getCourseDto();
        return Set.of(course);
    }
    
    private CourseDto getCourseDto() {
        return CourseDto.builder()
                        .name("random name")
                        .description("random description")
                        .build();
    }
    
}
