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
    private TypedQuery<User> typedQuery;

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
        typedQuery = em.createQuery("select u from User u where u.login = ?1",
                User.class);

        typedQuery.setParameter(1, login);
        typedQuery.setMaxResults(1);
        List<User> list = typedQuery.getResultList();

        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public List<User> getAll() {
        return this.em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    
    public EntityManager getEm() {
        return em;
    }
    
    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public TypedQuery<User> getTypedQuery() {
        return typedQuery;
    }
    
    public void setTypedQuery(TypedQuery<User> typedQuery) {
        this.typedQuery = typedQuery;
    }
}
