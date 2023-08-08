package booktopiahub.service;

import booktopiahub.dto.BookDto;
import booktopiahub.dto.BookSearchParametersDto;
import booktopiahub.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> getAll();

    BookDto findById(Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParametersDto params);

    BookDto update(Long id, CreateBookRequestDto requestDto);
}
