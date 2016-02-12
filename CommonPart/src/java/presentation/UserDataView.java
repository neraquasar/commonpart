/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import advanced.Statics;
import boundary.LogsFacade;
import boundary.UserDataFacade;
import entities.Logs;
import entities.Organisation;
import entities.UserData;
import entities.UserRole;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author k
 */
@ManagedBean
@ViewScoped
public class UserDataView extends AbstractManagedBean<UserData>{
    @EJB
    private LogsFacade logsFacade;
    
    @EJB
    private UserDataFacade objectFacade;
    
    UserRole roleTmp;
    
    
    /**
     * Creates a new instance of UserDataView
     */
    public UserDataView() {
        rootdir="user";
        object = new UserData();
    }
    
    @Override
    public void defineObject(){
        object = objectFacade.find(Long.parseLong(getParam(1)));
    }
    
    @Override
    public Set<UserData> getList_objects_all(){
        return new TreeSet<>(objectFacade.findAll());
    }
    
    @Override
    public UserData getObject(Number id){
        return objectFacade.find(id.longValue());
    }

    @Override
    public Collection<UserData> getList_objects_exclude() {
        return getList_objects_exclude(objectFacade.findAll_active(), 'l');
    }
    
    public String post_do(Long photo, UserData currentuser){
        object.setId(null);
        object.setPasshash("");
        object.setActive(Boolean.TRUE);
        objectFacade.create(object);
        logsFacade.create(new Logs((short)1, null, (short)1, new Date(), currentuser.getId(), object.toString()));
        return getBack();
    }
    
    public String edit_do(Long photo, UserData currentuser){
        UserData u2 = objectFacade.find(object.getId());
        if (photo!=null && !photo.equals(object.getPhoto()))
            object.setPhoto(photo);
        objectFacade.edit(object);
        logsFacade.create(new Logs((short)1, object.getId(), (short)2, new Date(), currentuser.getId(), object.getDifference(u2)));
        return getBack();
    }
    
    public void onRoleAdded(SelectEvent event) {
        UserRole role = (UserRole) event.getObject();
        object.addRole(role);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Добавлены права", role.getName());   
        FacesContext.getCurrentInstance().addMessage(null, message);        
    }
    
    public void onOrganisationAdded(SelectEvent event) {
        Organisation org = (Organisation) event.getObject();
        object.addOrganisation(roleTmp, org);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Добавлено учреждение", org.getName());   
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public void activate (UserData item, UserData currentuser){
        item.setActive(Boolean.TRUE);
        objectFacade.edit(item);
        logsFacade.create(new Logs((short)1, item.getId(), (short)5, new Date(), currentuser.getId()));
    }
    
    public void deactivate (UserData item, UserData currentuser){
        item.setActive(Boolean.FALSE);
        objectFacade.edit(item);
        logsFacade.create(new Logs((short)1, item.getId(), (short)4, new Date(), currentuser.getId()));
    }
    
    public void erasePass(UserData item, UserData userCurrent) {
        item.setPasshash("");
        objectFacade.edit(item);
        logsFacade.create(new Logs((short)1, item.getId(), (short)2, new Date(), userCurrent.getId(), Statics.string("s_password_Erased")));
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.string("s_password_Erased"), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().renderResponse();
    }

    public UserRole getRoleTmp() {
        return roleTmp;
    }

    public void setRoleTmp(UserRole roleTmp) {
        this.roleTmp = roleTmp;
    }
}
