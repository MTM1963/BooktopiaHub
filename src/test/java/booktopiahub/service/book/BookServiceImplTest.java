package booktopiahub.service.book;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import booktopiahub.dto.book.BookDto;
import booktopiahub.dto.book.CreateBookRequestDto;
import booktopiahub.exception.EntityNotFoundException;
import booktopiahub.mapper.BookMapper;
import booktopiahub.model.Book;
import booktopiahub.repository.book.BookRepository;
import booktopiahub.repository.book.BookSpecificationBuilder;
import java.util.List;
import java.util.Optional;
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
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testSuccessfulSave() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        BookDto bookDto = new BookDto();
        Book bookToSave = new Book();

        when(bookMapper.toModel(requestDto)).thenReturn(bookToSave);
        when(bookRepository.save(bookToSave)).thenReturn(bookToSave);
        when(bookMapper.toDto(bookToSave)).thenReturn(bookDto);

        BookDto result = bookService.save(requestDto);

        assertNotNull(result);
    }

    @Test
    public void testGetAllBooks() {
        Book book = new Book();
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = List.of(new Book());
        List<BookDto> expectedBooks = List.of(new BookDto());
        Page<Book> page = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.toDto(book)).thenReturn(new BookDto());

        List<BookDto> result = bookService.getAll(pageable);

        Assertions.assertEquals(expectedBooks.size(), result.size());
    }

    @Test
    public void testFindBookById() {
        Long bookId = 1L;
        Book book = new Book();
        BookDto bookDto = new BookDto();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.findById(bookId);

        Assertions.assertEquals(book.getId(), result.getId());
    }

    @Test
    public void testFindBookByNonExistedId() {
        Long bookId = 50L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.findById(bookId));
    }

    @Test
    public void testDeleteById() {
        Long bookId = 1L;
        bookService.deleteById(bookId);
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> bookService.findById(bookId));
    }

    @Test
    public void testUpdateBook() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated");
        requestDto.setAuthor("Updated");

        BookDto expectedResult = new BookDto();
        expectedResult.setTitle("Updated");
        expectedResult.setAuthor("Updated");

        Book book = new Book();
        book.setTitle("Old title");
        book.setAuthor("Old author");

        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expectedResult);

        BookDto updatedBookDto = bookService.update(bookId, requestDto);

        Assertions.assertEquals(updatedBookDto.getTitle(), expectedResult.getTitle());
        Assertions.assertEquals(updatedBookDto.getAuthor(), expectedResult.getAuthor());
    }
}
