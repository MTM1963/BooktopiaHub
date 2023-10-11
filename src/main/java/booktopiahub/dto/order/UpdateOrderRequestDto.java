package booktopiahub.dto.order;

import booktopiahub.model.Order;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateOrderRequestDto {
    private Order.Status status;
}
