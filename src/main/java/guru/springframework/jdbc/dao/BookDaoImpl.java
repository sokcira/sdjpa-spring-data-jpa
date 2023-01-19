package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import guru.springframework.jdbc.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BookDaoImpl implements BookDao {

    private final BookRepository bookRepository;

    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveNewBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.getReferenceById(id);
    }

    @Override
    @Transactional
    public Book updateBook(Book saved) {
        if(bookRepository.existsById(saved.getId())){
            Book book = bookRepository.getReferenceById(saved.getId());
            book.setIsbn(saved.getIsbn());
            book.setAuthorId(saved.getAuthorId());
            book.setTitle(saved.getTitle());
            book.setPublisher(saved.getPublisher());
            return bookRepository.save(book);
        }
        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository
                .findBookByTitle(title)
                .orElseThrow(EntityNotFoundException::new);
    }
}
