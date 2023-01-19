package guru.springframework.jdbc.repositories;

import guru.springframework.jdbc.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByTitle(String title);

    // created a package.info file and defined NonNullApi
    // then we can use here @Nullable stuff...
    @Nullable
    Book getByTitle(@Nullable String title);

    Book readByTitle(String title);

    // Find all books with title not null
    // this the jpa query we can write
    Stream<Book> findAllByTitleNotNull();
    @Async
    Future<Book> queryBookByTitle(String title);

    // ?1 = positional parameter
    @Query("SELECT b FROM Book b where b.title = ?1") // this is called HQL
    Book findBookByTitleWithQuery(String title);

    // the same as above but this time we use named parameter - so not ?1
    //@Param("title") must match the part in the query b.title = :title
    @Query("SELECT b FROM Book b where b.title = :title") // HQL
    Book queryBookByTitleWithQueryNamedParam(@Param("title") String bookTitle);

    // NATIVE SQL
    @Query(value = "SELECT * FROM book where title = :title", nativeQuery = true)
    Book queryBookByTitleNativeQuery(@Param("title") String bookTitle);
}
