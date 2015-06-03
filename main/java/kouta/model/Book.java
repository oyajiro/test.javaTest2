/**
 * 
 */
package kouta.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author user
 *
 */
@Entity
@Table(name="books")
public class Book implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = -5762575163852496747L;

    @Id
	@GeneratedValue
	private int id;
	@Column
	private String name;
	
	public Book() {
	}
	public Book(String name) {
		this.name = name;
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