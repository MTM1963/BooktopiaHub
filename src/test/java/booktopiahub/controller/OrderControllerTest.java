package booktopiahub.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import booktopiahub.dto.order.CreateOrderRequestDto;
import booktopiahub.dto.order.OrderDto;
import booktopiahub.dto.order.UpdateOrderRequestDto;
import booktopiahub.model.Order;
import booktopiahub.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
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
            @Autowired DataSource dataSource,
            @Autowired WebApplicationContext applicationContext
    ) throws SQLException {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        teardown(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/add-default-orders.sql")
            );
        }
    }

    @AfterAll
    static void afterAll(
            @Autowired DataSource dataSource
    ) {
        teardown(dataSource);
    }

    @SneakyThrows
    static void teardown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/delete-from-orders.sql")
            );
        }
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getAll_GivenOrdersInCatalog() throws Exception {
        OrderDto orderFirst = new OrderDto()
                .setId(1L)
                .setUserId(1L)
                .setTotal(BigDecimal.valueOf(50))
                .setStatus(Order.Status.PENDING)
                .setOrderDate(LocalDate.now())
                .setOrderItems(Set.of())
                .setShippingAddress("Kyiv, Ukraine");

        OrderDto orderSecond = new OrderDto()
                .setId(2L)
                .setUserId(2L)
                .setTotal(BigDecimal.valueOf(60))
                .setStatus(Order.Status.PENDING)
                .setOrderDate(LocalDate.now())
                .setOrderItems(Set.of())
                .setShippingAddress("Lviv, Ukraine");

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
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getOrderById() throws Exception {
        Long orderId = 1L;
        User user = new User();
        user.setId(1L);
        OrderDto expected = new OrderDto()
                .setId(orderId)
                .setUserId(user.getId())
                .setTotal(BigDecimal.valueOf(50))
                .setStatus(Order.Status.PENDING)
                .setOrderDate(LocalDate.now())
                .setOrderItems(Set.of())
                .setShippingAddress("Kyiv, Ukraine");

        MvcResult result = mockMvc.perform(get("/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        OrderDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), OrderDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void getByUserId() throws Exception {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        OrderDto expected = new OrderDto()
                .setId(1L)
                .setUserId(user.getId())
                .setTotal(BigDecimal.valueOf(50))
                .setStatus(Order.Status.PENDING)
                .setOrderDate(LocalDate.now())
                .setOrderItems(Set.of())
                .setShippingAddress("Kyiv, Ukraine");

        MvcResult result = mockMvc.perform(get("/orders/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        OrderDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), OrderDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    void testUpdateOrderStatus() throws Exception {
        UpdateOrderRequestDto requestDto = new UpdateOrderRequestDto()
                .setStatus(Order.Status.DELIVERED);

        Long orderId = 1L;

        OrderDto updated = new OrderDto()
                .setId(orderId)
                .setStatus(requestDto.getStatus());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(put("/orders/{id}", orderId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        OrderDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), OrderDto.class);

        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        EqualsBuilder.reflectionEquals(updated, actual, "id");
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Sql(
            scripts = "classpath:database/delete-test-order.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    void saveBook_ValidRequestDto_Success() throws Exception {
        CreateOrderRequestDto requestDto = new CreateOrderRequestDto()
                .setShippingAddress("Lviv, Ukraine");

        OrderDto expected = new OrderDto()
                .setShippingAddress("Lviv, Ukraine");

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
}
