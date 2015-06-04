package kouta;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import kouta.entity.Book;
import kouta.service.BookService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {
    BookService bookService;
    @Mock
    EntityManager entityManager;
    @Mock
    TypedQuery<Book> typedQuery;

    @Before
    public void setUp() {
        bookService = new BookService();
        bookService.setEm(entityManager);
        bookService.setTypedQuery(typedQuery);
    }

    @Test
    public void testGetAll() {
        List<Book> dummyResult = new LinkedList<Book>();
        when(entityManager.createQuery("SELECT b FROM Book b WHERE b.user.id = ?1", Book.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter(1, 0)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(dummyResult);

        // let's call findAll() and see what it does
        List<Book> result = bookService.getAll(0);

        // did it return the result list of the named query?
        assertSame(dummyResult, result);
    }

    @Test
    public void testChangeStatus() {
        Book book = new Book();
        book.setId(1);
        book.setStatus(0);
        when(entityManager.find(Book.class, 0)).thenReturn(book);
        assertEquals(book.getStatus(), 0);
        bookService.changeStatus(0, 1);
        assertEquals(book.getStatus(), 1);
    }

}
