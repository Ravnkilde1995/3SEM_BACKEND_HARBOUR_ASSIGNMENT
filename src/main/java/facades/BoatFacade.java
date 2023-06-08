package facades;

import dtos.BoatDTO;

import entities.Boat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public BoatDTO create(BoatDTO bd) {
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

    public long getBoatCount(){
        EntityManager em = getEntityManager();
        try{
            long boatCount = (long)em.createQuery("SELECT COUNT(r) FROM Boat r").getSingleResult();
            return boatCount;
        }finally{
            em.close();
        }
    }


}
