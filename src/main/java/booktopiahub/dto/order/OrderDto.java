package booktopiahub.dto.order;

import booktopiahub.model.Order;
import booktopiahub.model.OrderItem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Set<OrderItem> orderItems;
    private LocalDate orderDate;
    private BigDecimal total;
    private Order.Status status;
}
