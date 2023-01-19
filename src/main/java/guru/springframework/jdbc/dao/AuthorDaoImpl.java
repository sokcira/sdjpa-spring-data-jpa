package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by jt on 8/28/21.
 */
@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepository;

    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.getReferenceById(id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }
    @Transactional
    @Override
    public Author updateAuthor(Author author) {

        if (authorRepository.existsById(author.getId())) {
            Author foundAuthor = authorRepository.getReferenceById(author.getId());
            foundAuthor.setFirstName(author.getFirstName());
            foundAuthor.setLastName(author.getLastName());
            return authorRepository.save(foundAuthor);
        }
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
