package br.com.iagocolodetti.heroi.controller;

import br.com.iagocolodetti.heroi.model.Heroi;
import br.com.iagocolodetti.heroi.Util;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author iagocolodetti
 */
@Stateless
@Path("universos")
public class UniversoFacadeREST {

    @PersistenceContext(unitName = "HeroisRESTPU")
    private EntityManager em;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAll() {
        javax.persistence.TypedQuery<Heroi> tq = em.createNamedQuery("Universo.findAll", Heroi.class);
        return Util.toJson(tq.getResultList());
    }
}
