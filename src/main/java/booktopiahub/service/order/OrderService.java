package booktopiahub.service.order;

import booktopiahub.dto.order.CreateOrderRequestDto;
import booktopiahub.dto.order.OrderDto;
import booktopiahub.dto.order.UpdateOrderRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto update(Long id, UpdateOrderRequestDto requestDto);

    OrderDto addOrder(CreateOrderRequestDto createOrderRequestDto);

    OrderDto getByUserId(Long userId);

    OrderDto findById(Long id);

    List<OrderDto> getAll(Pageable pageable);

    OrderDto getByOrderIdAndOrderItemId(Long orderId, Long orderItemsId);
}
