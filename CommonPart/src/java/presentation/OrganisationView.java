/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.LogsFacade;
import boundary.OrganisationFacade;
import entities.Logs;
import entities.Organisation;
import entities.UserData;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author k
 */
@ManagedBean
@ViewScoped
public class OrganisationView extends AbstractManagedBean<Organisation> {
    @EJB
    private LogsFacade logsFacade;
    @EJB
    private OrganisationFacade objectFacade;
    
    /**
     * Creates a new instance of OrganisationView
     */
    public OrganisationView() {
        rootdir = "org";
        object = new Organisation();
    }
    
    @Override
    public Organisation getObject(Number id){
        return objectFacade.find(id.longValue());
    }
    
    @Override
    public void defineObject(){
        object = objectFacade.find(Long.parseLong(getParam(1)));
    }
    
    @Override
    public Set<Organisation> getList_objects_all(){
        return new TreeSet<>(objectFacade.findAll());
    }
    
    @Override
    public Set<Organisation> getList_objects_exclude() {
        return new TreeSet<>(getList_objects_exclude(objectFacade.findAll_active(), 'l'));
    }
    
    public String post_do(UserData currentuser){
        object.setId(null);
        object.setActive(Boolean.TRUE);
        objectFacade.create(object);
        logsFacade.create(new Logs((short)2, null, (short)1, new Date(), currentuser.getId(), object.toString()));
        return getBack();
    }
    
    public String edit_do(UserData currentuser){
        Organisation obj2 = objectFacade.find(object.getId());
        objectFacade.edit(object);
        logsFacade.create(new Logs((short)2, object.getId(), (short)2, new Date(), currentuser.getId(), object.getDifference(obj2)));
        return getBack();
    }
    
    
    public void activate (Organisation item, UserData currentuser){
        item.setActive(Boolean.TRUE);
        objectFacade.edit(item);
        logsFacade.create(new Logs((short)2, item.getId(), (short)5, new Date(), currentuser.getId()));
    }
    
    public void deactivate (Organisation item, UserData currentuser){
        item.setActive(Boolean.FALSE);
        objectFacade.edit(item);
        logsFacade.create(new Logs((short)2, item.getId(), (short)4, new Date(), currentuser.getId()));
    }

    
}
