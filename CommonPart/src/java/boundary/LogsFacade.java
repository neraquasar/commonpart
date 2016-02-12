/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Logs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author k
 */
@Stateless
public class LogsFacade extends AbstractFacade<Logs> {
    @PersistenceContext(unitName = "CommonPartPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LogsFacade() {
        super(Logs.class);
    }
    
    @Override
    public List<Logs> findAll(){
        return (em.createQuery(
        "SELECT c FROM Logs c ORDER BY C.timeofaction DESC")
                .getResultList());
    }
    
}
