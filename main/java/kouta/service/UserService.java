package kouta.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kouta.entity.User;

@Component
public class UserService {
    @PersistenceContext
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * Save user, if user new, do persist. Otherwise do merge.
     * @param user
     */
    @Transactional
    public void save(User user) {
        if (user.getIsNew()) {
            System.out.println("new");
            this.em.persist(user);
        } else {
            System.out.println("exist");
            this.em.merge(user);
        }
    }

    @Transactional
    public User read(int id) {
        return this.em.find(User.class, id);
    }

    @Transactional
    public void remove(User user) {
        User loadUser = this.em.find(User.class, user.getId());
        em.remove(loadUser);
    }

    @Transactional
    public User findByLogin(String login) {
        TypedQuery<User> query = em.createQuery("select u from User u where u.login = ?1",
                User.class);

        query.setParameter(1, login);
        query.setMaxResults(1);
        List<User> list = query.getResultList();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public List<User> getAll() {
        return this.em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}
