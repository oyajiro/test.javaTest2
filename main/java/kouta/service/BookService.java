package kouta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kouta.model.Book;

@Component
public class BookService {
	@PersistenceContext
    private EntityManager em;
 
    public EntityManager getEm() {
        return em;
    }
 
    public void setEm(EntityManager em) {
        this.em = em;
    }
 
    @Transactional
    public void save(Book book) {
        this.em.persist(book);
    }
    
    @Transactional
    public Book read(int id) {
    	return this.em.find(Book.class, id);
    }
    
    public List<Book> getAll() {
    	return this.em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }
    
    @Transactional
    public void remove(Book book) {
        Book loadBook = this.em.find(Book.class, book.getId());
        em.remove(loadBook);
    }
}
