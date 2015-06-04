package kouta.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import kouta.service.BookService;
import kouta.entity.Book;

/**
 * @author kouta
 */
@ManagedBean
@SessionScoped
public class BookController implements Serializable {

    private static final long serialVersionUID = 8251481382599432437L;
    @Autowired
    private BookService bookService;
    private HashMap<Integer, String> statuses;
    @ManagedProperty(value = "#{userController}")
    private UserController userController;
    private Book book = new Book();
    

    public BookController() {
        WebApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext
                .getCurrentInstance());
        bookService = ctx.getBean(BookService.class);

        HashMap<Integer, String> statuses = new HashMap<Integer, String>();
        statuses.put(0, "new");
        statuses.put(1, "approve");
        statuses.put(1, "decline");
        this.statuses = statuses;
    }

    public String getStatus() {
        return this.statuses.get(book.getStatus());
    }

    public String getStatus(Integer status) {
        return this.statuses.get(status);
    }

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

    public String add() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        FacesMessage message = null;
        message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Creating error",
                "Not enough rights");

        if (session.getAttribute("role") != null && (Integer) session.getAttribute("role") == 1
                && userController.getUser() != null) {
            book.setStatus(0);
            book.setUser(userController.getUser());
            bookService.save(book);

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "The Book "
                    + this.book.getName() + " Is Registered Successfully");
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "index?faces-redirect=true";
    }

    public void approve(Integer bookId) {
        bookService.changeStatus(bookId, 1);
    }

    public void decline(Integer bookId) {
        bookService.changeStatus(bookId, 2);
    }

    public void retry(Integer bookId) {
        bookService.changeStatus(bookId, 0);
    }

    public UserController getUserController() {
        return userController;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public List<Book> getMyBooks() {
        return bookService.getAll(userController.getUser().getId());
    }

    public List<Book> getWaiting() {
        return bookService.getListByStatus(0);
    }

    public List<Book> getApproved() {
        return bookService.getListByStatus(1);
    }

    public void delete(Book book) {
        bookService.remove(book);
    }

}
