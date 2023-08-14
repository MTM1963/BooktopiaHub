package booktopiahub.dto.order;

import booktopiahub.model.Order;
import lombok.Data;

@Data
public class UpdateOrderRequestDto {
    private Order.Status status;
}
