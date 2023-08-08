package booktopiahub.service;

import booktopiahub.dto.BookDto;
import booktopiahub.dto.BookSearchParametersDto;
import booktopiahub.dto.CreateBookRequestDto;
import booktopiahub.exception.EntityNotFoundException;
import booktopiahub.mapper.BookMapper;
import booktopiahub.model.Book;
import booktopiahub.repository.book.BookRepository;
import booktopiahub.repository.book.BookSpecificationBuilder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream().map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto params) {
        Specification<Book> bookSpecification = bookSpecificationBuilder.build(params);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        Book existedBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id " + id));
        existedBook.setTitle(requestDto.getTitle());
        existedBook.setAuthor(requestDto.getAuthor());
        existedBook.setIsbn(requestDto.getIsbn());
        existedBook.setPrice(requestDto.getPrice());
        existedBook.setDescription(requestDto.getDescription());
        existedBook.setCoverImage(requestDto.getCoverImage());
        return bookMapper.toDto(bookRepository.save(existedBook));
    }
}
