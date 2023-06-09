package rest;

import entities.Boat;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BoatResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    Boat b1, b2, b3, b4;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        b1 = new Boat("Bianca", "m3", "Frode","jpeg");
        b2 = new Boat("Bandholm", "24", "Gurli", "jpeg");
        b3 = new Boat("Blue Dane", "Blue Leth","Gitte","jpeg");
        b4 = new Boat("Compass", "31", "Gundhil", "jpeg");
        try {
            em.getTransaction().begin();
            //Delete existing Boats to get a "fresh" database
            em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
            em.persist(b1);
            em.persist(b2);
            em.persist(b3);
            em.persist(b4);

            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given()
                .when()
                .get("/boat")
                .then()
                .statusCode(200);
    }

    // Rest assured test that verifies that the endpoint returns the number of rows in the Boat table.
    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/boat").then()
                .assertThat()
                .statusCode(200).body("size()", org.hamcrest.Matchers.is(1));
    }

    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/boat/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello boat"));
    }

    // Rest assured test that verifies that the endpoint returns the correct Boat.
    @Test
    public void testGetCarById() throws Exception {
        given()
                .contentType("application/json")
                .get("/boat/{id}", b1.getId()).then()
                .assertThat()
                .body("brand", equalTo("Bianca"))
                .body("make", equalTo("m3"))
                .body("name", equalTo("Frode"))
                .body("image", equalTo("jpeg"));

    }
}
