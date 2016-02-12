/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valconv;

import advanced.Statics;
import boundary.OrganisationFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author k
 */
@FacesValidator("valconv.ValidatorOrgNameExist")
public class ValidatorOrgNameExist implements Validator {
    OrganisationFacade organisationFacade = lookupOrganisationFacadeBean();
    
    public ValidatorOrgNameExist(){
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        if (value.toString().equals(component.getAttributes().get("currentvalue"))) return;
        //Для редактирования: если текущее имя пользователя совпадает с вводимым, выходить из валидатора
        //если не совпадает, то искать в базе данных, чтобы не двоилось
        //"атрибут" (как и все <f:...>) вычисляется в момент построения экранной формы, он не изменяется после изменения значения строки
        //а поэтому достаточно его один раз вытащить из текущего пользователя
        if (!organisationFacade.find_byName(Statics.firstUpperCase(value.toString())).isEmpty()) {
            FacesMessage msg = new FacesMessage(Statics.string("Organisation_f_OrgNameExists"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    private OrganisationFacade lookupOrganisationFacadeBean() {
        try {
            Context c = new InitialContext();
            return (OrganisationFacade) c.lookup("java:global/CommonPart/OrganisationFacade!boundary.OrganisationFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
