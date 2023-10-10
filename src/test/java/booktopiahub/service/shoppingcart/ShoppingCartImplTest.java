package booktopiahub.service.shoppingcart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import booktopiahub.dto.shoppingcart.ShoppingCartDto;
import booktopiahub.mapper.ShoppingCartMapper;
import booktopiahub.model.ShoppingCart;
import booktopiahub.repository.shoppingcart.ShoppingCartRepository;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ShoppingCartImplTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @InjectMocks
    private ShoppingCartImpl shoppingCartService;

    @Test
    public void testGetByUserId() {
        Long userId = 1L;
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        ShoppingCartDto expectedDto = new ShoppingCartDto();
        when(shoppingCartRepository.findByUserId(userId)).thenReturn(shoppingCart);
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(expectedDto);

        ShoppingCartDto resultDto = shoppingCartService.getByUserId(userId);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    public void testGetByUserIdNotFound() {
        Long userId = 2L;
        when(shoppingCartRepository.findByUserId(userId)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> shoppingCartService.getByUserId(userId));
    }

    @Test
    public void testGetAll() {
        ShoppingCart shoppingCart = new ShoppingCart();
        Pageable pageable = PageRequest.of(0, 10);
        List<ShoppingCart> carts = List.of(new ShoppingCart());
        List<ShoppingCartDto> expectedCarts = List.of(new ShoppingCartDto());
        Page<ShoppingCart> page = new PageImpl<>(carts, pageable, carts.size());

        when(shoppingCartRepository.findAll(pageable)).thenReturn(page);
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(new ShoppingCartDto());

        List<ShoppingCartDto> result = shoppingCartService.getAll(pageable);

        Assertions.assertEquals(expectedCarts.size(), result.size());
    }
}
