package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import entities.Boat;
import facades.BoatFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;

@Path("boat")
public class BoatResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final BoatFacade boatFacade = BoatFacade.getBoatFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello boat\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allBoats() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<Boat> query = em.createQuery("select u from Boat u", entities.Boat.class);
            List<Boat> boats = query.getResultList();
            return "[" + boats.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String getBoatById(@PathParam("id") long id) {
        BoatDTO bdto = boatFacade.getBoutById(id);
        bdto.setId(id);
        return GSON.toJson(bdto);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createBoat(String content) {
        BoatDTO bd = GSON.fromJson(content, BoatDTO.class);
        BoatDTO b = boatFacade.create(bd);
        return Response.ok(GSON.toJson(new Boat(b.getBrand(),b.getMake(),b.getName(),b.getImage()))).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response updateBoat(@PathParam("id") long id, String input) throws Exception {
        BoatDTO bdto = GSON.fromJson(input, BoatDTO.class);
        bdto = boatFacade.updateBoat(id, bdto);
        bdto.setId(id);
        return Response.ok().entity(bdto).build();
    }

   /*  @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("{id}")
    public Response deleteBoat(@PathParam("id") long id, String input) throws Exception {
       /* BoatDTO bdto = GSON.fromJson(input, BoatDTO.class);
        bdto = boatFacade.removeBoat(id);
        bdto.setId(id);
        return Response.ok().entity(bdto).build();
    }*/
}
