/**
 * 
 */
package kouta.controller;


import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
 
import kouta.model.Book;
/**
 * @author user
 *
 */
public class index {

	public static void main(String[] args) {
//		Configuration cfg = new Configuration();
//		cfg.configure("hibernate.cfg.xml");
//		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
//	            cfg.getProperties()).build();
//		SessionFactory sf = cfg.buildSessionFactory(serviceRegistry);
//		Session session = sf.openSession();
//		Transaction tr = session.beginTransaction();
//		Book book = new Book();
//		book.setName("testmenow");
//		session.save(book);
//		session.flush();
//		tr.commit();
//		session.close();
//		System.out.println("done");
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
//                "applicationContext.xml");
//        BookDao dao = context.getBean(BookDao.class);
// 
//        Book second = new Book("Second");
// 
//        // Add new Book records
//        dao.save(second);
// 
//        // Count Book records
//        System.out.println("Count Book records: " + dao.count());
// 
//        // Print all records
//        List<Book> Books = (List<Book>) dao.findAll();
//        for (Book Book : Books) {
//            System.out.println(Book);
//        }
// 
//        // Update Book
//        second.setName("Secondless");
//        dao.save(second);
// 
//        System.out.println("Find by id 1: " + dao.findOne(1));
// 
//        // Remove record from Book
//        dao.delete(1);
// 
//        // And finally count records
//        System.out.println("Count Book records: " + dao.count());
// 
//        context.close();
	}

}
