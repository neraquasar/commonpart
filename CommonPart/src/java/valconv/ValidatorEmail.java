/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valconv;

import advanced.Statics;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
@FacesValidator("valconv.ValidatorEmail")
public class ValidatorEmail implements Validator {
    
    private static final String EMAIL_PATTERN =
            "^[A-Za-z]{1}[_A-Za-z0-9\\-]*(\\.[_A-Za-z0-9\\-]+)*@{1}[A-Za-z0-9][A-Za-z0-9\\-]*(\\.[A-Za-z0-9\\-]+)*[A-Za-z]+[0-9]*(\\.[A-Za-z]{2,})$";
    //^ - начинается с...
    //[] - одно из значений, указанных во внутренностях
    //[A-Za-z]{1} - больший или маленькие латинские буквы, ровно 1 символ
    //[_A-Za-z0-9\\-]* - знак "_", больший или маленькие латинские буквы, цифры, знак "-", 0 или сколько угодно раз
    //(\\.[_A-Za-z0-9\\-]+)* - точка, а за ней <предыдущая строка>, один или несколько символов, а вся эта группа с точкой - 0 или несколько раз
    //@{1} - собака, ровно 1 шт.
    //[A-Za-z0-9] - один из символов, обязателен
    //[A-Za-z0-9\\-]* - всякое (описано выше), 0 или несколько символов
    //(\\.[A-Za-z0-9\\-]+)* - точка, а за ней всякое 1 или несколько символов, а вся группа 0 или несколько повторений
    //[A-Za-z]+ - один из указанных символов не менее 1 раза
    //[0-9]* цифра, 0 или несколько символов
    //(\\.[A-Za-z]{2,})$"; - точка, а за ней от 2 до бесконечности букв
    //$ - конец
    
    private Pattern pattern;
    private Matcher matcher;
    
    public ValidatorEmail(){
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
        throws ValidatorException
    {
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {
            FacesMessage msg = new FacesMessage(Statics.string("main_f_eMailFormatIncorrect"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        

        
    }
    
}
