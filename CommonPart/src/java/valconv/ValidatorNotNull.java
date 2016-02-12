/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valconv;

import advanced.Statics;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author k
 */
@FacesValidator("valconv.ValidatorNotNull")
public class ValidatorNotNull implements Validator {
    
    
    public ValidatorNotNull(){
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        if (value==null || "".equals(value.toString())) {
            FacesMessage msg = new FacesMessage(Statics.string("main_f_NotNull"));
            msg.setSeverity(FacesMessage.SEVERITY_WARN);
            throw new ValidatorException(msg);
        }
        

        
    }
    
}
