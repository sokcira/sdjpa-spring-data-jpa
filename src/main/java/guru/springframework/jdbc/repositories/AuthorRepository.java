package guru.springframework.jdbc.repositories;


import guru.springframework.jdbc.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    // data jpa is creating under the hood the query based on method name and parameters on the object Author
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);
}
