/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.UserRoleFacade;
import entities.UserRole;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author k
 */
@ManagedBean
@RequestScoped
public class UserRoleView extends AbstractManagedBean<UserRole> {
    @EJB
    private UserRoleFacade objectFacade;

    /**
     * Creates a new instance of UserRoleView
     */
    public UserRoleView() {
        rootdir = "role";
    }

    @Override
    public void defineObject() {
        //не требуется, т.к. роли не меняются из пользовательского окружения
    }

    @Override
    public UserRole getObject(Number id) {
        return objectFacade.find(id.shortValue());
    }

    @Override
    public Set<UserRole> getList_objects_all() {
        return new TreeSet<>(objectFacade.findAll());
    }
    
    @Override
    public Set<UserRole> getList_objects_exclude() {
        return new TreeSet<>(getList_objects_exclude(objectFacade.findAll_active(),'s'));
    }
}
