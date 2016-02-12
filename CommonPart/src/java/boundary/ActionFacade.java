/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.Action;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author k
 */
@Stateless
public class ActionFacade extends AbstractFacade<Action> {
    @PersistenceContext(unitName = "CommonPartPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActionFacade() {
        super(Action.class);
    }
    
    public List<String> findAll_name (){
        return (em.createQuery("SELECT c.name FROM Action c").getResultList());
    }
}
