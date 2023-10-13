package booktopiahub.service.shoppingcart.cartitem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import booktopiahub.dto.shoppingcart.cartitem.CartItemDto;
import booktopiahub.dto.shoppingcart.cartitem.CreateCartItemRequestDto;
import booktopiahub.dto.shoppingcart.cartitem.UpdateCartItemDto;
import booktopiahub.exception.EntityNotFoundException;
import booktopiahub.mapper.CartItemMapper;
import booktopiahub.model.Book;
import booktopiahub.model.CartItem;
import booktopiahub.repository.shoppingcart.cartitem.CartItemRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartItemMapper cartItemMapper;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @Test
    public void testGetByBookId() {
        Long bookId = 1L;

        Book book = new Book();
        book.setId(bookId);
        book.setTitle("New");
        book.setAuthor("New");

        CartItem cartItem = new CartItem();
        cartItem.setBook(book);

        CartItemDto expectedDto = new CartItemDto();
        when(cartItemRepository.findByBookId(bookId)).thenReturn(cartItem);
        when(cartItemMapper.toDto(cartItem)).thenReturn(expectedDto);

        CartItemDto resultDto = cartItemService.getByBookId(bookId);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    public void testGetByBookId_NonExistingCartItem() {
        Long bookId = 2L;
        when(cartItemRepository.findByBookId(bookId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> cartItemService.getByBookId(bookId));
    }

    @Test
    public void testUpdateCart() {
        UpdateCartItemDto updatedRequestDto = new UpdateCartItemDto();
        updatedRequestDto.setQuantity(5);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(3);
        Long id = 1L;

        CartItemDto expectedResult = new CartItemDto();
        expectedResult.setQuantity(5);

        when(cartItemRepository.findById(id)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
        when(cartItemMapper.toDto(cartItem)).thenReturn(expectedResult);

        CartItemDto updatedCartItemDto = cartItemService.update(id, updatedRequestDto);

        Assertions.assertEquals(updatedCartItemDto.getQuantity(), expectedResult.getQuantity());
    }

    @Test
    public void testUpdate_NonExistingCartItem() {
        Long id = 2L;
        UpdateCartItemDto updateCartItemDto = new UpdateCartItemDto();
        when(cartItemRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> cartItemService.update(id, updateCartItemDto));
    }

    @Test
    public void testAddBook() {
        CreateCartItemRequestDto requestDto = new CreateCartItemRequestDto();
        CartItem cartItem = new CartItem();
        CartItemDto cartItemDto = new CartItemDto();
        when(cartItemMapper.toModel(requestDto)).thenReturn(cartItem);
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
        when(cartItemMapper.toDto(cartItem)).thenReturn(cartItemDto
        );
        CartItemDto result = cartItemService.addBook(requestDto);
        assertNotNull(result);
    }

    @Test
    public void deleteById() {
        Long id = 1L;
        cartItemService.deleteById(id);

        verify(cartItemRepository, times(1)).deleteById(id);
    }
}
