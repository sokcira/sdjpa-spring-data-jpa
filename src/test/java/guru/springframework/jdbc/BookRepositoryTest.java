package guru.springframework.jdbc;

import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = "guru.springframework.jdbc.dao")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testQueryBookByTitleWithNativeSQLQuery() {
        Book book = bookRepository.queryBookByTitleNativeQuery("Clean Code");
        assertNotNull(book);
    }

    @Test
    void testQueryBookByTitleWithQueryNamedParam() {
        Book book = bookRepository.queryBookByTitleWithQueryNamedParam("Clean Code");

        assertNotNull(book);
    }

    @Test
    void testFindBookByTitleWithAtQuery() {
        Book book = bookRepository.findBookByTitleWithQuery("Clean Code");

        assertNotNull(book);
    }

    @Test
    void testBookFutureAsync() {
        Future<Book> bookFuture = bookRepository.queryBookByTitle("Clean Code");

        try {
            Book book = bookFuture.get();
            assertNotNull(book);
            assertEquals(bookFuture.get().getTitle(), book.getTitle());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testStreamGetAllBooksWithTitles() {
        AtomicInteger count = new AtomicInteger();

        bookRepository.findAllByTitleNotNull().forEach(book -> {
            count.incrementAndGet();
        });

        assertThat(count.get()).isGreaterThan(0);
    }

    @Test
    void testEmptyResultException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
           bookRepository.readByTitle("Foobarr");
        });
    }

    @Test
    void testNullParam() {
        assertNull(bookRepository.getByTitle(null));
    }

    @Test
    void testNoException() {
        assertNull(bookRepository.getByTitle("foo"));
    }
}
