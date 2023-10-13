package booktopiahub.dto.shoppingcart;

import booktopiahub.model.CartItem;
import java.util.Set;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItem> cartItemSet;
}
