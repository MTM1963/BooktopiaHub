package booktopiahub;

import booktopiahub.service.book.BookService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class BooktopiaHubApplication {
    private final BookService bookService;

    public BooktopiaHubApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BooktopiaHubApplication.class, args);
    }
}
