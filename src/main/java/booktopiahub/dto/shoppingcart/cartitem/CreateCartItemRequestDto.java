package booktopiahub.dto.shoppingcart.cartitem;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateCartItemRequestDto {
    private Long bookId;
    private int quantity;
    private Long shoppingCartId;
}
