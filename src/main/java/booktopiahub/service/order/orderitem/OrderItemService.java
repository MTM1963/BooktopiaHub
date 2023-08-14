package booktopiahub.service.order.orderitem;

import booktopiahub.dto.order.orderitem.OrderItemDto;

public interface OrderItemService {

    OrderItemDto getByBookId(Long bookId);

    OrderItemDto findById(Long id);

    void deleteById(Long id);
}
