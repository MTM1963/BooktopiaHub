package booktopiahub.service.shoppingcart.cartitem;

import booktopiahub.dto.shoppingcart.cartitem.CartItemDto;
import booktopiahub.dto.shoppingcart.cartitem.CreateCartItemRequestDto;
import booktopiahub.dto.shoppingcart.cartitem.UpdateCartItemDto;
import booktopiahub.exception.EntityNotFoundException;
import booktopiahub.mapper.CartItemMapper;
import booktopiahub.model.CartItem;
import booktopiahub.repository.shoppingcart.cartitem.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    @Override
    public CartItemDto getByBookId(Long bookId) {
        CartItem cartItem = cartItemRepository.findByBookId(bookId);
        if (cartItem == null) {
            throw new RuntimeException("Can't find cartItem by bookId " + bookId);
        }
        return cartItemMapper.toDto(cartItem);
    }

    @Override
    public CartItemDto update(Long id, UpdateCartItemDto updateCartItemDto) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find cartItem by id " + id));
        cartItem.setQuantity(updateCartItemDto.getQuantity());
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public CartItemDto addBook(CreateCartItemRequestDto cartItemRequestDto) {
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }
}
