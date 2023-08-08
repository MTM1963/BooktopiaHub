package booktopiahub.service;

import booktopiahub.dto.BookDto;
import booktopiahub.dto.BookSearchParametersDto;
import booktopiahub.dto.CreateBookRequestDto;
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
