package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.api.BookRequest;
import ru.gb.model.Book;
import ru.gb.repository.BookRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> showAllBooks() {
        return bookRepository.findAll();
    }

    public Book addNewBook(BookRequest request) {
        // TODO: необходимо продумать момент с дублирующимися книгами, добавить counter для учёта количества экземпляров
        if (bookRepository.findBookByName(request.getName()) != null) {
            throw new IllegalArgumentException("Экземпляр данной книги уже есть");
        }
        Book book = new Book(request.getName());
        bookRepository.save(book);
        return book;
    }

    public Book showBookInfo(long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Книга с id: " + id + " не найдена"));
    }

    public Book deleteBook(long id) {
        Book book = showBookInfo(id);
        if (book == null) {
            throw new NoSuchElementException("Книга с id: " + id + " не найдена");
        }
        bookRepository.deleteById(id);
        return book;
    }
}