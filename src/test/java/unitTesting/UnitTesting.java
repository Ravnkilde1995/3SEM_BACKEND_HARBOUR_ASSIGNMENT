package unitTesting;

import entities.Boat;
import entities.Owner;
import entities.RenameMe;
import facades.FacadeExample;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTesting {

    private static EntityManagerFactory emf;
    private static FacadeExample facade;

    public UnitTesting() {
    }

    @BeforeAll
    public static void setUpClass() {
        //emf = EMF_Creator.createEntityManagerFactoryForTest();
        //facade = FacadeExample.getFacadeExample(emf);
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
            em.createNamedQuery("RenameMe.deleteAllRows").executeUpdate();
            em.persist(new RenameMe("Some txt", "More text"));
            em.persist(new RenameMe("aaa", "bbb"));

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Boat b1 = new Boat("107", "Bianca", "Luna", "billed");
        Boat b2 = new Boat("24", "Bandholm", "Tris", "billed");
        Boat b3 = new Boat("101", "Bianca ", "Aphrodite", "billed");

        Owner o1 = new Owner("Hans", "langgade 22", 21212121);
        Owner o2 = new Owner("Troels", "lilletoften 117", 21212121);
        Owner o3 = new Owner("Erik", "lilletoften 120", 21212121);

        b1.addOwners(o1);
        b2.addOwners(o2);
        b3.addOwners(o3);
        b1.addOwners(o3);

        em.getTransaction().begin();
        em.persist(b1);
        em.persist(b2);
        em.persist(b3);
        em.getTransaction().commit();
        em.close();
    }
}
