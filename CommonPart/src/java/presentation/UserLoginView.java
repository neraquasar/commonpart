/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import boundary.UserDataFacade;
import advanced.Statics;
import boundary.LogsFacade;
import entities.Logs;
import entities.UserData;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author neraquasar
 */
@ManagedBean
@SessionScoped
public class UserLoginView implements Serializable {
    @EJB
    private LogsFacade logsFacade;
    @EJB
    private UserDataFacade objectFacade;
    private UserData object;
    private String login;//нужен отдельно чтобы 1) не запариваться за очистку переменной "текущий пользователь"; 2) чтобы можо было оставлять введенное значение при ошибке ввода пароля - человеку не придется снова его вводить
    private String password;
    private String passwordNew;
    private String passwordNew2;

    /**
     * Creates a new instance of UsersView
     */
    public UserLoginView() {
        this.object = new UserData();
        this.password = new String();
        this.passwordNew = new String();
        this.passwordNew2 = new String();
    }

    public Boolean isLogged(){
        return object.getLogin()!=null && !"".equals(object.getLogin());
    }
    
    public void login_start_dialog() {
        login="";
        password="";
        Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        //options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 150);
        options.put("contentWidth", 900);
        //options.put("showEffect", "");
        options.put("includeViewParams", false);
        
        RequestContext.getCurrentInstance().openDialog("/userLogin/login.xhtml", options, null);
    }
    
    public void clearUser() {
        setPassword(new String());
        setPasswordNew(new String());
        setPasswordNew2(new String());
        setObject(new UserData());//не в нулл, т.к. нужно использовать isLogged => нужен экземпляр, у которого смотреть логин
    }
    
    /*public String login_do(){
        for (UserData user : userDataFacade.findAll()) {//проверка, есть ли cреди имён пользователей такое, как внёс пользователь в поле
            if (Objects.equals(user.getLogin(), login)) {//если имя пользователя встречается, то считаем, что пользователь есть, работаем дальше
                if (passwordValidation(user, password)) {//если пароль введен верно
                    if (Objects.equals(user.getActive(), Boolean.FALSE)) {//если СНИЛС+пароль верны, но пользователь отключен, то нужно его отфутболить
                        setResult(Statics.user_isInactive(user));
                        clearUser();
                        return "loginResult";
                    }
                    if (password == null || "".equals(password)) {//если текущий пароль поллльзователя пуст, нужно принудительно  заставить его установить его себе. До установки пароля не следует определять, что пользователь вошел и имеет такие-то роли, чтобы не допустить работу в системе с пустым паролем. Факт входа и список ролей будет определен в момент установки пароля.
                        password = "";
                        setUserCurrent(user);
                        object.setLogin("");//имя пользователя сбрасывается, чтобы польозватель не считался вошедшим
                        object.setRolestructure(null);//роли отбираются, чтобы невозможно былополучить прямой доступ к страницам по прямой ссылке
                        return "passwordReset";
                    } else {//если СНИЛС + пароль (непустой) верны, то установить, что пользователь вошел и определить список ролей
                        password = "";
                        setUserCurrent(user);
                        setResult(Statics.logingDone(user));
                        return "loginResult";
                    }
                } else {//если пароль введен неверно, выдаём собщение о неверном вводе
                    clearUser();
                    setResult(Statics.wrong_LoginOrPass());
                    return "loginResult";
                }
            }//если имя пользователя не встречается, то считаем, что пользователя нет, выдаем сообщение о неверном вводе
        }
        clearUser();
        setResult(Statics.wrong_LoginOrPass());
        return "loginResult";
    }*/
    
    /*public String login_do(){
        List<UserData> userlist = userDataFacade.find_byLogin(login);
        if (userlist.size()>1) {
            FacesMessage msg = new FacesMessage("Что-то нетак со справочником пользователей: имена пользователей задвоены");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if (userlist.isEmpty()) {
            FacesMessage msg = new FacesMessage("Модуль проверки не отработал отсутствие имени пользователя");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        object=userlist.get(0);
        if (password == null || "".equals(password)) {//если текущий пароль поллльзователя пуст, нужно принудительно  заставить его установить его себе. До установки пароля не следует определять, что пользователь вошел и имеет такие-то роли, чтобы не допустить работу в системе с пустым паролем. Факт входа и список ролей будет определен в момент установки пароля.
            password = "";
            object.setLogin("");//имя пользователя сбрасывается, чтобы польозватель не считался вошедшим
            object.setRolestructure(null);//роли отбираются, чтобы невозможно былополучить прямой доступ к страницам по прямой ссылке
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.password_SetNeeded(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().renderResponse();
            
            return "passwordReset";
        } else {//если СНИЛС + пароль (непустой) верны, то установить, что пользователь вошел и определить список ролей
            password = "";
            setResult(Statics.logingDone(object));
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.logingDone(object), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().renderResponse();
            return "/index";
        }
    }*/
    
    public String getLoginActionName(){
        return !isLogged() ? Statics.string("Login_i_loginAction_Enter") : Statics.string("Login_i_loginAction_Exit");
    }
    
    public void loginAction_start(){
        if (!isLogged()) login_start_dialog();
        else exit_start_dialog();
    }
    
    public String login_do_dialog(){
        List<UserData> userlist = objectFacade.find_byLogin(login);
        if (userlist.size()>1) {
            FacesMessage msg = new FacesMessage(Statics.string("Login_e_LoginDoubling"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        if (userlist.isEmpty()) {
            FacesMessage msg = new FacesMessage(Statics.string("Login_e_LoginNullPassed"));
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        object=userlist.get(0);
        if (object.getPasshash() == null || "".equals(object.getPasshash())) {//если текущий пароль пользователя пуст, нужно принудительно  заставить его установить его себе. До установки пароля не следует определять, что пользователь вошел и имеет такие-то роли, чтобы не допустить работу в системе с пустым паролем. Факт входа и список ролей будет определен в момент установки пароля.
            password = "";
            object = new UserData(object.getId()); //убрать всё кроме ИДа, чтобы без создания пароля пользователь был практически не залогиненым
            object.setPasshash("");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.string("Login_f_Password_SetNeeded"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().renderResponse();
            
            return "passwordReset";
        } else {//если СНИЛС + пароль (непустой) верны, то установить, что пользователь вошел и определить список ролей
            password = "";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.string("Login_s_logingDone_Text1")+object.getName() + " " + object.getPatronymic(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().renderResponse();
            RequestContext.getCurrentInstance().closeDialog(object);
            return "";
        }
    }
    
    public void onLoginAction(SelectEvent event) {
        if (isLogged()){
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.string("Login_s_logingDone_Text1")+object.getName() + " " + object.getPatronymic(), "");   
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.string("Login_s_unLoging_Done"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void onPasswordCange(SelectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.string("Login_s_password_Changed"), "");   
        FacesContext.getCurrentInstance().addMessage(null, message);        
    }
    
    public void validateLoginPass(ComponentSystemEvent e) {
        UIForm form = (UIForm) e.getComponent();
        UIInput loginInput = (UIInput) form.findComponent("login");
        UIInput pwdInput = (UIInput) form.findComponent("password");
        String login = loginInput==null ? "" : loginInput.getValue()==null ? "" : loginInput.getValue().toString();
        String pass = pwdInput==null ? "" : pwdInput.getValue()==null ? "" : pwdInput.getValue().toString();

        for (UserData user : objectFacade.findAll()) {//проверка, есть ли cреди имён пользователей такое, как внёс пользователь в поле
            if (Objects.equals(user.getLogin(), login))//если имя пользователя встречается, то считаем, что пользователь есть, работаем дальше
                if (Statics.passwordValidation(user, pass)){
                    if (user.getActive()) return;
                    else {
                        returnLoginError(2);
                        return;
                    }
                }
                else {
                    returnLoginError(1);
                    return;
                }
        }
        returnLoginError(1);
    }
    
    private void returnLoginError(int reason){
        FacesMessage msg;
        switch (reason){
            //не найдено имя пользователя или неверный пароль
            case 1: msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, Statics.string("Login_f_LoginOPass_Wrond"), ""); break;
            //пользователь отключён
            case 2: msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Statics.string("UserData_f_User_IsInactive"), ""); break;
            //на случай глюков
            default: msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, Statics.string("Login_e_LoginOrPass_UnknownError"), ""); break;
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().renderResponse();
    }
    
    /*public String resetPassword_start(){
        if (isLogged())
            login = object.getLogin();
        return "/userLogin/passwordReset";
    }*/
    
    public void resetPassword_start_dialog(){
        if (isLogged())
            login = object.getLogin();
        Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        //options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 200);
        options.put("contentWidth", 800);
        //options.put("showEffect", "");
        options.put("includeViewParams", false);
        
        RequestContext.getCurrentInstance().openDialog("/userLogin/passwordReset.xhtml", options, null);
    }
    
    /*public String resetPassword_do() {
        if ("".equals(object.getPasshash())) {//если изначальный пароль был пустым, то при внесении нового пароля нужно установить, что пользователь вошёл и имеет такие-то роли. Ранее это специально не делалось из cоображений безопасности: с пустым паролем нельзя работать в системе.        
            setUserCurrent(userDataFacade.find(object.getId()));
        }
        repostHash(object.getId(), passwordNew);
        setResult(Statics.str_NewPassPosted());
        
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.logingDone(object), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().renderResponse();
        //pageToGo = "/index";
        return "/index";
    }*/
    
    public void resetPassword_do_dialog() {
        FacesMessage msg;
        if ("".equals(object.getPasshash())) {//если изначальный пароль был пустым, то при внесении нового пароля нужно установить, что пользователь вошёл и имеет такие-то роли. Ранее это специально не делалось из cоображений безопасности: с пустым паролем нельзя работать в системе.        
            setObject(objectFacade.find(object.getId()));
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.string("Login_s_logingDone_Text1")+object.getName() + " " + object.getPatronymic(), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        repostHash(object.getId(), passwordNew);
        password="";
        passwordNew="";
        passwordNew2="";
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.string("Login_s_password_Changed"), "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().renderResponse();
        RequestContext.getCurrentInstance().closeDialog(object);
    }
    
    private void repostHash (Long id, String password){
        object.setPasshash(Statics.calculateHashString(password));
        objectFacade.edit(object);
        logsFacade.create(new Logs((short)1, object.getId(), (short)2, new Date(), object.getId(), Statics.string("Login_s_password_Changed")));
    }

    /*public String resetPassword_cancel() {
        if (object.getPasshash() == null || "".equals(object.getPasshash())) {
            clearUser();
            return "/index";
        } else {
            passwordNew="";
            passwordNew2="";
            return "/index";
        }
    }*/
    
    public void exit_start_dialog() {
        Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        //options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 150);
        options.put("contentWidth", 400);
        //options.put("showEffect", "");
        options.put("includeViewParams", false);
        RequestContext.getCurrentInstance().openDialog("/userLogin/exit.xhtml", options, null);
    }
    
    public void exit_do(){
        clearUser();
        RequestContext.getCurrentInstance().closeDialog(Boolean.TRUE);
        //FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, Statics.unLoging_Done(), "");
        //FacesContext.getCurrentInstance().addMessage(null, msg);
        //FacesContext.getCurrentInstance().renderResponse();
    }
    
    public void exit_cancel(){
        RequestContext.getCurrentInstance().closeDialog(Boolean.FALSE);
    }
    
    public void validatePassEquals(ComponentSystemEvent e) {
        UIForm form = (UIForm) e.getComponent();
        UIInput pass2Input = (UIInput) form.findComponent("pass2");
        UIInput pass3Input = (UIInput) form.findComponent("pass3");

        if (Objects.equals(pass2Input.getValue(), pass3Input.getValue())) return;
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, Statics.string("Login_f_password_NotEqualsNewPass1NewPass2"), "");   
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().renderResponse();
    }

    public UserData getObject() {
        return object;
    }

    public void setObject(UserData object) {
        this.object = object;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordNew() {
        return passwordNew;
    }

    public void setPasswordNew(String passwordNew) {
        this.passwordNew = passwordNew;
    }

    public String getPasswordNew2() {
        return passwordNew2;
    }

    public void setPasswordNew2(String passwordNew2) {
        this.passwordNew2 = passwordNew2;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    

}
