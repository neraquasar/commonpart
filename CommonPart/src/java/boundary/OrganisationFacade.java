/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Organisation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author k
 */
@Stateless
public class OrganisationFacade extends AbstractFacade<Organisation> {
    @PersistenceContext(unitName = "CommonPartPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrganisationFacade() {
        super(Organisation.class);
    }
    
    public List<Organisation> findAll_active() {
        return (em.createQuery(
        "SELECT c FROM Organisation c WHERE c.active=TRUE")
                .getResultList());
    }
    
    public List<Organisation> find_byName(String name) {
        return (em.createQuery(
        "SELECT c FROM Organisation c WHERE c.name=:name")
                .setParameter("name", name)
                .getResultList());
    }
}
