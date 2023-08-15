package booktopiahub.dto.shoppingcart;

import booktopiahub.model.CartItem;
import java.util.Set;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItemSet;
}
