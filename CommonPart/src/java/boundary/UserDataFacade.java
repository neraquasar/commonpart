/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import entities.UserData;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author k
 */
@Stateless
public class UserDataFacade extends AbstractFacade<UserData> {
    @PersistenceContext(unitName = "CommonPartPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserDataFacade() {
        super(UserData.class);
    }
    
    public List<UserData> find_byLogin(String login) {
        return (em.createQuery(
        "SELECT c FROM UserData c WHERE c.login=:login")
                .setParameter("login", login)
                .getResultList());
    }
    
    public List<UserData> findAll_active() {
        return (em.createQuery(
        "SELECT c FROM UserData c WHERE c.active=TRUE")
                .getResultList());
    }
    
}
