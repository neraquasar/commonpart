/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import advanced.Statics;
import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;

/**
 *
 * @author k
 */
@ManagedBean
@ApplicationScoped
public class GeneralNShared {

    TimeZone timeZoneCurrent;
    TimeZone timeZone0;
    
    public GeneralNShared() {
        timeZoneCurrent = TimeZone.getDefault();
        timeZone0 = TimeZone.getDefault();
        timeZone0.setRawOffset(0);
    }
    
    public TimeZone getTimeZomeCurrent() { 
       return timeZoneCurrent;
    }
    
    public TimeZone getTimeZome0() { 
       return timeZone0;
    }
    
    /*public String getStr_e_IncorrectRoleToWork(){
        return Strings.e_IncorrectRoleToWork;
    }*/
    
    public long getFileSize (){
        return Statics.documentToUpload_MAX_FILE_SIZE;
    }
    
    /*public String getStr_e_FileSizeExceeded(){
        return Strings.e_FileSizeExceeded;
    }*/
    
    public String getStr_e_FileLimitExceeded(int n){
        return Statics.string("f_FileLimitExceeded_Text1") + n + Statics.string("f_FileLimitExceeded_Text2");
    }
    
    
    public String style_active_text (Boolean active){
        return active ? Statics.string("format_style_black_text") : Statics.string("format_style_gray_text");
    }
    
}
