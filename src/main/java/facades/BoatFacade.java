package facades;

import dtos.BoatDTO;

import entities.Boat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class BoatFacade {

    private static BoatFacade instance;
    private static EntityManagerFactory emf;

    private BoatFacade() {

    }

    public static BoatFacade getBoatFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BoatFacade();
        }
        return instance;
    }

    private static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static BoatDTO create(BoatDTO bd) {
        Boat b = new Boat(bd.getMake(), bd.getBrand(), bd.getName(), bd.getImage());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(b);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BoatDTO(b);
    }

    public BoatDTO updateBoat(long id, BoatDTO bdto) {
        EntityManager em = emf.createEntityManager();
        Boat boat = em.find(Boat.class, id);
        boat.setName(bdto.getName());
        boat.setMake(bdto.getMake());
        boat.setBrand(bdto.getBrand());
        boat.setImage(bdto.getImage());
        //boat.setOwner(bdto.getOwners());
        //boat.setHarbour(bdto.get);

        try {
            em.getTransaction().begin();
            em.merge(boat);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new BoatDTO(boat);

    }

    public List<BoatDTO> getAllBoats() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b", Boat.class);
        List<Boat> b = query.getResultList();
        em.close();
        return BoatDTO.getDtos(b);
    }

    public BoatDTO getBoutById(long id) {
        EntityManager em = emf.createEntityManager();
        Boat boat = em.find(Boat.class, id);
        return new BoatDTO(boat);
    }

    public long getBoatCount() {
        EntityManager em = getEntityManager();
        try {
            long boatCount = (long) em.createQuery("SELECT COUNT(r) FROM Boat r").getSingleResult();
            return boatCount;
        } finally {
            em.close();
        }
    }
}
