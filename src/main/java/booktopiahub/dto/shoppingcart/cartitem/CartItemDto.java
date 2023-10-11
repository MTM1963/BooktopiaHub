package booktopiahub.dto.shoppingcart.cartitem;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CartItemDto {
    private Long id;
    private Long bookId;
    private Long shoppingCartId;
    private int quantity;
}
