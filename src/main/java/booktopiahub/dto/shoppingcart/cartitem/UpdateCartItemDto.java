package booktopiahub.dto.shoppingcart.cartitem;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UpdateCartItemDto {
    private int quantity;
}
