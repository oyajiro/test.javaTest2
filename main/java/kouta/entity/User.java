/**
 * 
 */
package kouta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author kouta
 * 
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = -6754166079187298493L;

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    /**
     * Check if user new or exist
     */
    @Transient
    private Boolean isNew = true;

    /**
     * Check if password was changed
     */
    @Transient
    private String newPassword = "";
    
    @Column
    private String fio;
    
    @Column
    private int role = 0;

    public String getPassword() {
        return password;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
