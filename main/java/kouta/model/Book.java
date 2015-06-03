/**
 * 
 */
package kouta.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author user
 * 
 */
@Entity
@Table(name = "books")
public class Book implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5762575163852496747L;

    @Id
    @GeneratedValue
    private int id;
    @Column(unique=true)
    private String name;

    @Column
    private int status = 0;
    
    @OneToOne
    @JoinColumn(name = "user_id") 
    private User user;   
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}