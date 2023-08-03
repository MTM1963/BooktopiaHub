package booktopiahub;

import booktopiahub.model.Book;
import booktopiahub.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BooktopiaHubApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BooktopiaHubApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book = new Book();
                book.setTitle("Pirates");
                book.setDescription("Pirates advantures");
                book.setAuthor("John Leck");
                book.setPrice(BigDecimal.valueOf(50));
                book.setIsbn("ISBN");
                book.setCoverImage("image");

                bookService.save(book);

                System.out.println(bookService.getAll());
            }
        };
    }
}
