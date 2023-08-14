package booktopiahub.service.shoppingcart.cartitem;

import booktopiahub.dto.shoppingcart.cartitem.CartItemDto;
import booktopiahub.dto.shoppingcart.cartitem.CreateCartItemRequestDto;
import booktopiahub.dto.shoppingcart.cartitem.UpdateCartItemDto;

public interface CartItemService {
    CartItemDto getByBookId(Long bookId);

    CartItemDto update(Long id, UpdateCartItemDto updateCartItemDto);

    CartItemDto addBook(CreateCartItemRequestDto cartItemRequestDto);

    void deleteById(Long id);
}
