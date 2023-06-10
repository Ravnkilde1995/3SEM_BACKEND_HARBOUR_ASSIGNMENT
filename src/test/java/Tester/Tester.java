package Tester;

import entities.Boat;
import entities.Harbour;
import entities.Owner;
import entities.RenameMe;
import facades.FacadeExample;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tester {

    /*private static EntityManagerFactory emf;
    private static FacadeExample facade;

    public Tester() {
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
    }*/

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Boat b1 = new Boat("107", "Bianca", "Luna", "billed");
        Boat b2 = new Boat("24", "Bandholm", "Tris", "billed");
        Boat b3 = new Boat("101", "Bianca ", "Aphrodite", "billed");

        Owner o1 = new Owner("Hans", "langgade 22", 21212121);
        Owner o2 = new Owner("Troels", "lilletoften 117", 21212121);
        Owner o3 = new Owner("Erik", "lilletoften 120", 21212121);

        // tester owners og både
        b1.addOwners(o1);
        b2.addOwners(o2);
        b3.addOwners(o3);
        b1.addOwners(o3);

        //tester på havn der har både
        Harbour h1 = new Harbour("rungsted havn", "rungsted havne vej 57",500);
        Harbour h2 = new Harbour("København havn", "københavnvej 51",300);

        h1.addBoats(b1);
        h2.addBoats(b2);
        h2.addBoats(b3);

        em.getTransaction().begin();
        em.persist(b1);
        em.persist(b2);
        em.persist(b3);
        em.persist(h1);
        em.persist(h2);
        em.getTransaction().commit();
        em.close();

        System.out.println("Hvad ligger der i rungstedhavn: " + h1.getBoats());
    }
}
