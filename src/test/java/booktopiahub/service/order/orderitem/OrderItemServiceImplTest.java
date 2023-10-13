package booktopiahub.service.order.orderitem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import booktopiahub.dto.order.orderitem.OrderItemDto;
import booktopiahub.exception.EntityNotFoundException;
import booktopiahub.mapper.OrderItemMapper;
import booktopiahub.model.Book;
import booktopiahub.model.OrderItem;
import booktopiahub.repository.order.orderitem.OrderItemRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    public void getByBookId() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Book Title");
        book.setAuthor("Book Author");

        OrderItem orderItem = new OrderItem();
        orderItem.setBook(book);

        OrderItemDto expectedDto = new OrderItemDto();
        when(orderItemRepository.findByBookId(bookId)).thenReturn(orderItem);
        when(orderItemMapper.toDto(orderItem)).thenReturn(expectedDto);

        OrderItemDto resultDto = orderItemService.getByBookId(bookId);
        assertEquals(expectedDto, resultDto);
    }

    @Test
    public void testGetByBookIdNotFound() {
        Long bookId = 2L;
        when(orderItemRepository.findByBookId(bookId)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> orderItemService.getByBookId(bookId));
    }

    @Test
    public void testFindOrderItemById() {
        Long orderItemId = 1L;
        OrderItem orderItem = new OrderItem();
        OrderItemDto orderItemDto = new OrderItemDto();

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(orderItem));
        when(orderItemMapper.toDto(orderItem)).thenReturn(orderItemDto);

        OrderItemDto result = orderItemService.findById(orderItemId);

        Assertions.assertEquals(orderItem.getId(), result.getId());
    }

    @Test
    public void testFindOrderItemByNonExistedId() {
        Long orderItemId = 50L;
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> orderItemService.findById(orderItemId));
    }

    @Test
    public void testDeleteOrderItemById() {
        Long orderItemId = 1L;
        orderItemService.deleteById(orderItemId);
        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> orderItemService.findById(orderItemId));
    }
}
