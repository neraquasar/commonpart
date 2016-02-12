/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.ActionFacade;
import boundary.EntityFacade;
import boundary.LogsFacade;
import boundary.UserDataFacade;
import entities.Logs;
import entities.UserData;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author k
 */
@ManagedBean
@ViewScoped
public class LogsView extends AbstractManagedBean<Logs>{
    @EJB
    private UserDataFacade userDataFacade;
    @EJB
    private EntityFacade entityFacade;
    @EJB
    private ActionFacade actionFacade;
    @EJB
    private LogsFacade objectFacade;

    /**
     * Creates a new instance of LogsView
     */
    public LogsView() {
        rootdir="log";
    }
    
    public String getEntityName (Short id){
        return entityFacade.find(id).getName();
    }
    
    public String getActionName (Short id){
        return actionFacade.find(id).getName();
    }
    
    public String getUser (Long id){
        UserData u = userDataFacade.find(id);
        return u.getSurname() + " "
                + u.getName() + " "
                + u.getPatronymic() +" "
                + "("+u.getLogin()+")";
    }
    
    public List<String> getActions(){
        return actionFacade.findAll_name();
    }

    @Override
    public void defineObject() {
        //не используется
    }

    @Override
    public Logs getObject(Number id) {
        return objectFacade.find(id.longValue());
    }

    @Override
    public Collection<Logs> getList_objects_all() {
        return objectFacade.findAll();
    }

    @Override
    public Collection<Logs> getList_objects_exclude() {
        //не используется
        return objectFacade.findAll();
    }
    
}
