package booktopiahub.service;

import booktopiahub.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> getAll();
}
