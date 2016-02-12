/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boundary;

import advanced.AbstractFacade;
import entities.Filesmeta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author k
 */
@Stateless
public class FilesmetaFacade extends AbstractFacade<Filesmeta> {
    @PersistenceContext(unitName = "CommonPartPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FilesmetaFacade() {
        super(Filesmeta.class);
    }
    
    public List<Filesmeta> find_byObject(Filesmeta meta) {
        return (em.createQuery(
        "SELECT c FROM Filesmeta c WHERE c.name=:name AND c.contenttype=:contenttype")
                .setParameter("name", meta.getName())
                .setParameter("contenttype", meta.getContenttype())
                //.setParameter("link", meta.getLink2data()) //в момент поиска привязки метаданных на байтовому содержанию еще не существует
                .getResultList());
    }
    
    public List<Filesmeta> find_byLink(Long link) {
        return (em.createQuery(
        "SELECT c FROM Filesmeta c WHERE c.link2data=:link")
                .setParameter("link", link)
                .getResultList());
    }
    
}
