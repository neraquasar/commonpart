/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package valconv;

import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author k
 */
@FacesConverter("valconv.ConverterDecimal")
public class ConverterDecimal implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        FacesMessage msg = new FacesMessage("Дробное число писать через запятую или точку, например: 123,4 123.4");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        
        String [] parts;
        if (value.contains(",")) parts = value.split(",");
        else {
            if (value.contains(".")) parts = value.split(".");
            else throw new ConverterException(msg);
                } 
        if (parts.length!=2) throw new ConverterException(msg);
        else{
            String sign = "+";
            String left="";
            String right="";
            
            parts[0] = parts[0].trim();
            
            if ("-".equals(parts[0].charAt(0))) sign = "-";
            for (char c : parts[0].toCharArray())
                if (Character.isDigit(c)) left = left+c;
            for (char c : parts[1].toCharArray())
                if (Character.isDigit(c)) right = right+c;
            
            BigDecimal b = new BigDecimal(sign+left+"."+right);
            return b;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
