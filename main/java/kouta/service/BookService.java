package kouta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kouta.entity.Book;

@Component
public class BookService {
    @PersistenceContext
    private EntityManager em;
    private TypedQuery<Book> typedQuery;

    @Transactional
    public void save(Book book) {
        this.em.persist(book);
    }

    @Transactional
    public Book read(int id) {
        return this.em.find(Book.class, id);
    }

    /**
     * @param userId
     * @return List of books for specified user
     */
    public List<Book> getAll(Integer userId) {
        typedQuery = em.createQuery("SELECT b FROM Book b WHERE b.user.id = ?1",
                Book.class);
        typedQuery.setParameter(1, userId);
        return typedQuery.getResultList();
    }

    public List<Book> getListByStatus(Integer status) {
        typedQuery = em.createQuery("SELECT b FROM Book b WHERE b.status = ?1",
                Book.class);
        typedQuery.setParameter(1, status);
        return typedQuery.getResultList();
    }

    @Transactional
    public void remove(Book book) {
        Book loadBook = this.em.find(Book.class, book.getId());
        em.remove(loadBook);
    }

    @Transactional
    public void changeStatus(Integer bookId, Integer newStatus) {
        Book book = em.find(Book.class, bookId);
        if (book != null) {
            book.setStatus(newStatus);
            em.merge(book);
        }
    }
    
    public EntityManager getEm() {
        return em;
    }
    
    public TypedQuery<Book> getTypedQuery() {
        return typedQuery;
    }
    
    public void setTypedQuery(TypedQuery<Book> typedQuery) {
        this.typedQuery = typedQuery;
    }
    
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
