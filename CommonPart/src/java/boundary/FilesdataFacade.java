/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import advanced.AbstractFacade;
import entities.Filesdata;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author k
 */
@Stateless
public class FilesdataFacade extends AbstractFacade<Filesdata> {
    @PersistenceContext(unitName = "CommonPartPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FilesdataFacade() {
        super(Filesdata.class);
    }
    
    public List<Filesdata> find_byHash(String hash) {
        return (em.createQuery(
        "SELECT c FROM Filesdata c WHERE c.hash=:hash")
                .setParameter("hash", hash)
                .getResultList());
    }
    
}
