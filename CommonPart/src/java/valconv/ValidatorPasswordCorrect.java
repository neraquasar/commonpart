/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valconv;

import advanced.Statics;
import boundary.UserDataFacade;
import entities.UserData;
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
@FacesValidator("valconv.ValidatorPasswordCorrect")
public class ValidatorPasswordCorrect implements Validator {
    UserDataFacade userDataFacade = lookupUserDataFacadeBean();
    
    public ValidatorPasswordCorrect(){
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException{
        if (Statics.passwordValidation((UserData)component.getAttributes().get("user"), value.toString())) return;
        else {
            FacesMessage msg = new FacesMessage(Statics.string("Login_f_PassCurrent_Incorrect"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

    private UserDataFacade lookupUserDataFacadeBean() {
        try {
            Context c = new InitialContext();
            return (UserDataFacade) c.lookup("java:global/CommonPart/UserDataFacade!boundary.UserDataFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
