package booktopiahub.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import booktopiahub.dto.order.CreateOrderRequestDto;
import booktopiahub.dto.order.OrderDto;
import booktopiahub.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {
    protected static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(
            @Autowired WebApplicationContext applicationContext
    ) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void getAll_GivenOrdersInCatalog() throws Exception {
        OrderDto orderFirst = new OrderDto();
        orderFirst.setId(1L);
        orderFirst.setUserId(1L);
        orderFirst.setTotal(BigDecimal.valueOf(50));
        orderFirst.setStatus(Order.Status.PENDING);
        orderFirst.setOrderDate(LocalDate.now());
        orderFirst.setOrderItems(Set.of());
        orderFirst.setShippingAddress("Kyiv, Ukraine");

        OrderDto orderSecond = new OrderDto();
        orderSecond.setId(2L);
        orderSecond.setUserId(2L);
        orderSecond.setTotal(BigDecimal.valueOf(60));
        orderSecond.setStatus(Order.Status.PENDING);
        orderSecond.setOrderDate(LocalDate.now());
        orderSecond.setOrderItems(Set.of());
        orderSecond.setShippingAddress("Lviv, Ukraine");

        List<OrderDto> expected = new ArrayList<>();
        expected.add(orderFirst);
        expected.add(orderSecond);

        MvcResult result = mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        OrderDto[] actual = objectMapper.readValue(result.getResponse()
                .getContentAsByteArray(), OrderDto[].class);
        Assertions.assertEquals(2, actual.length);
        Assertions.assertEquals(expected, Arrays.stream(actual).toList());
    }

    @Test
    void getOrderById() {
    }

    @Test
    void getByUserId() {
    }

    @Test
    void update() {
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void saveBook_ValidRequestDto_Success() throws Exception {
        CreateOrderRequestDto requestDto = new CreateOrderRequestDto();
        requestDto.setShippingAddress("125 Lviv, Ukraine");

        OrderDto expected = new OrderDto();
        expected.setShippingAddress("125 Lviv, Ukraine");

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post("/orders")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        OrderDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), OrderDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    void getOrderItemByOrderIdAndItemId() {
    }
}
