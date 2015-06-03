package kouta.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import kouta.service.BookService;
import kouta.model.Book;
 
@ManagedBean
@SessionScoped
public class BookController implements Serializable {
 
	/**
     * 
     */
    private static final long serialVersionUID = 8251481382599432437L;
    @Autowired
    private BookService bookService;

    public BookController() {
    	WebApplicationContext ctx =  FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
    	bookService = ctx.getBean(BookService.class);
	}

    private Book book = new Book();
 
    public BookService getbookService() {
        return bookService;
    }
 
    public void setbookService(BookService bookService) {
        this.bookService = bookService;
    }
 
    public Book getBook() {
        return book;
    }
 
    public void setBook(Book book) {
        this.book = book;
    }
 
    public void add() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        FacesMessage message = null;
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Creating error", "Not enough rights");

        if (session.getAttribute("role") != null && (Integer)session.getAttribute("role") == 1) {
            	// Calling Business Service
                bookService.save(book);
                // Add message
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "The Book " + this.book.getName() + " Is Registered Successfully");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public List<Book> getBooks() {
        return bookService.getAll();
    }
    
    public void delete(Book book) {
        bookService.remove(book);
    }
    
}
