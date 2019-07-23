package br.com.iagocolodetti.heroi.service;

import br.com.iagocolodetti.heroi.Heroi;
import br.com.iagocolodetti.heroi.Util;
import com.google.gson.JsonSyntaxException;
import java.util.Iterator;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author iagocolodetti
 */
@Stateless
@Path("heroi")
public class HeroiFacadeREST {

    @PersistenceContext(unitName = "HeroiRESTPU")
    private EntityManager em;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String add(String heroiJson) {
        String resposta = "";
        try {
            Heroi heroi = Util.heroiFromJson(heroiJson);
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Heroi>> constraintViolations = validator.validate(heroi);
            if(constraintViolations.size() > 0){
                Iterator<ConstraintViolation<Heroi>> iterator = constraintViolations.iterator();
                while(iterator.hasNext()){
                    ConstraintViolation<Heroi> cv = iterator.next();
                    String erro = cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage();
                    System.err.println(erro);
                    resposta = (resposta.isEmpty() ? erro : "\n" + erro);
                }
            } else {
                em.persist(heroi);
                resposta = "Herói adicionado com sucesso.";
            }
        } catch (JsonSyntaxException | EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
            resposta = e.getMessage();
            e.printStackTrace();
        }
        return resposta;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String logicalDelete(@PathParam("id") Integer id) {
        String resposta = "";
        try {
            TypedQuery<Heroi> tq = em.createNamedQuery("Heroi.logicalDelete", Heroi.class);
            tq.setParameter("id", id);
            resposta = (tq.executeUpdate() > 0 ? "Herói excluído com sucesso." : "Herói não excluído.");
        } catch (IllegalStateException | PersistenceException e) {
            resposta = e.getMessage();
            e.printStackTrace();
        }
        return resposta;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAllAtivo() {
        TypedQuery<Heroi> tq = em.createNamedQuery("Heroi.findAllAtivo", Heroi.class);
        return Util.toJson(tq.getResultList());
    }
}
