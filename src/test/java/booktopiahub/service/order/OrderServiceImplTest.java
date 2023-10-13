package booktopiahub.service.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import booktopiahub.dto.order.CreateOrderRequestDto;
import booktopiahub.dto.order.OrderDto;
import booktopiahub.dto.order.UpdateOrderRequestDto;
import booktopiahub.exception.EntityNotFoundException;
import booktopiahub.mapper.OrderMapper;
import booktopiahub.model.Order;
import booktopiahub.repository.order.OrderRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testOrderUpdate() {
        UpdateOrderRequestDto requestDto = new UpdateOrderRequestDto();
        requestDto.setStatus(Order.Status.PROCESSING);

        OrderDto orderDto = new OrderDto();
        orderDto.setStatus(Order.Status.PROCESSING);

        Order order = new Order();
        order.setStatus(Order.Status.PENDING);

        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.update(orderId, requestDto);

        Assertions.assertEquals(result.getStatus(), orderDto.getStatus());
    }

    @Test
    public void testAddOrder() {
        CreateOrderRequestDto requestDto = new CreateOrderRequestDto();
        OrderDto orderDto = new OrderDto();
        Order order = new Order();

        when(orderMapper.toModel(requestDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.addOrder(requestDto);

        assertNotNull(result);
    }

    @Test
    public void testGetByUserId() {
        Long userId = 1L;
        Order order = new Order();
        order.setId(1L);
        OrderDto expectedDto = new OrderDto();
        when(orderRepository.findByUserId(userId)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(expectedDto);

        OrderDto resultDto = orderService.getByUserId(userId);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    public void testGetByUserIdNotFound() {
        Long userId = 2L;
        when(orderRepository.findByUserId(userId)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> orderService.getByUserId(userId));
    }

    @Test
    public void testFindOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        OrderDto orderDto = new OrderDto();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        OrderDto result = orderService.findById(orderId);

        Assertions.assertEquals(order.getId(), result.getId());
    }

    @Test
    public void testFindOrderByNonExistedId() {
        Long orderId = 50L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> orderService.findById(orderId));
    }

    @Test
    public void testGetAllOrders() {
        Order order = new Order();
        Pageable pageable = PageRequest.of(0, 10);
        List<Order> orders = List.of(new Order());
        List<OrderDto> expectedOrders = List.of(new OrderDto());
        Page<Order> page = new PageImpl<>(orders, pageable, orders.size());

        when(orderRepository.findAll(pageable)).thenReturn(page);
        when(orderMapper.toDto(order)).thenReturn(new OrderDto());

        List<OrderDto> result = orderService.getAll(pageable);

        Assertions.assertEquals(expectedOrders.size(), result.size());
    }
}
