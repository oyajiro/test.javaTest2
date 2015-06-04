package kouta;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import kouta.entity.User;
import kouta.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    UserService userService;
    @Mock
    EntityManager entityManager;
    @Mock
    TypedQuery<User> typedQuery;

    @Before
    public void setUp() {
        userService = new UserService();
        userService.setEm(entityManager);
        userService.setTypedQuery(typedQuery);
    }
    @Test
    public void testFindByLogin() {
        List<User> dummyResult = new LinkedList<User>();
        User dummyUser = new User();
        dummyResult.add(dummyUser);
        when(entityManager.createQuery("select u from User u where u.login = ?1", User.class))
                .thenReturn(typedQuery);
        when(typedQuery.setParameter(1, "a")).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(dummyResult);

        User user = userService.findByLogin("a");
        assertSame(dummyUser, user);
        when(typedQuery.setParameter(1, "b")).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(null);

        User user2 = userService.findByLogin("b");
        assertSame(null, user2);
        
    }

}
