package booktopiahub.dto.order;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateOrderRequestDto {
    private String shippingAddress;
}
