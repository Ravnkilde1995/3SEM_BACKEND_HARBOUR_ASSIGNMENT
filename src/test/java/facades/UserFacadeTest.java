package facades;

import entities.Boat;
import entities.Role;
import entities.User;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.persist(new User("test","test"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method
    @Test
    public void testAFacadeMethod() throws Exception {
        List<User> uf = new ArrayList<>();
        uf.add(facade.getUser("test"));
        assertEquals(1, uf.size());
    }

    @Test
    public void create() throws Exception {

        EntityManager em = emf.createEntityManager();
        try {
            //Role userRole = new Role("user");
            User user = new User("username", "password");
            //user.addRole(userRole);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void update() throws Exception {

        /*BoatDTO bdto = new BoatDTO();
        bdto =

        assertEquals(1, facade.updateBoat(1, bdto));
        */
    }
}
