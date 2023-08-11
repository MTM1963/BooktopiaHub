package booktopiahub.service;

import booktopiahub.dto.book.BookDto;
import booktopiahub.dto.book.BookSearchParametersDto;
import booktopiahub.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto params);

    BookDto update(Long id, CreateBookRequestDto requestDto);
}
