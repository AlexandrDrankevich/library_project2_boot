package by.it.spring.services;

import by.it.spring.models.Book;
import by.it.spring.models.Person;
import by.it.spring.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear) {
        if(sortByYear) return bookRepository.findAll(Sort.by("year"));
        return bookRepository.findAll();
    }

    public  List<Book> findAllSortedWithPagination(Integer pageNumber, Integer booksPerPage, boolean sortByYear) {
        if(sortByYear) return bookRepository.findAll(PageRequest.of(pageNumber,booksPerPage,Sort.by("year"))).getContent();
    return bookRepository.findAll(PageRequest.of(pageNumber,booksPerPage)).getContent();
    }

    public Book show(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void update(Book updatedBook) {
        Book book = bookRepository.findById(updatedBook.getId()).get();
        updatedBook.setOwner(book.getOwner());
        updatedBook.setCaptureDate(book.getCaptureDate());
        bookRepository.save(updatedBook);
    }

    public Person getOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(null);
        book.setCaptureDate(null);
    }

    @Transactional
    public void assign(int id, Person person) {
        Book book = bookRepository.findById(id).get();
        book.setOwner(person);
        book.setCaptureDate(new Date());
    }


    public List<Book> findBookByNameStart(String searchParam) {
       return bookRepository.findByNameStartingWith(searchParam);
    }
}
