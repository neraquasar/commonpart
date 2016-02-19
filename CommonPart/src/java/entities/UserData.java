/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import advanced.Statics;
import advanced.RoleOrgs;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author k
 */
@Entity
@Table(name = "userdata")
@NamedQueries({
    @NamedQuery(name = "UserData.findAll", query = "SELECT u FROM UserData u")})
public class UserData extends AbstractEntity implements Serializable, Comparable<UserData> {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "surname")
    private String surname;
    @Size(max = 2147483647)
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "photo")
    private Long photo;
    @Size(max = 2147483647)
    @Column(name = "login")
    private String login;
    @Size(max = 2147483647)
    @Column(name = "passhash")
    private String passhash;
    @Size(max = 2147483647)
    @Column(name = "rolestring")
    private String rolestring;
    @Column(name = "active")
    private Boolean active;

    public UserData() {
    }
    
    public UserData(Long id) {
        this.id = id;
    }

    public SortedSet<RoleOrgs> getRolestructure() {
        SortedSet<RoleOrgs> rolestructure = new TreeSet<>();
        if (rolestring!=null && !rolestring.equals("")){
            String[] parts = rolestring.split(Statics.delimiter_1);
            for (String p : parts){
                rolestructure.add(new RoleOrgs(p));
            }
        }
        return rolestructure;
    }
    
    public void setRolestructure(SortedSet<RoleOrgs> rolestructure){
        String d = Statics.delimiter_1;
        String s = "";
        if (rolestructure==null) setRolestring(s);
        else{
            for (RoleOrgs ro : rolestructure)
                s = s + ro.getOriginal() + d;
            setRolestring(Statics.cropLastDelimiter(s, d));
                }
    }
    
    public Set<UserRole> getRoles(){
        Set<UserRole> set = new TreeSet<>();
        for (RoleOrgs ro : getRolestructure())
            set.add(ro.getRole());
        return set;
    }
    
    public Set<Short> getRoles_ID(){
        Set<Short> set = new HashSet<>();
        for (UserRole r : getRoles())
            set.add(r.getId());
        return set;
    }
    
    public void addRole(UserRole role){
        SortedSet<RoleOrgs> rs = getRolestructure(); 
        rs.add(new RoleOrgs(role));
        setRolestructure(rs);
    }
    
    public void removeRole(UserRole role){
        SortedSet<RoleOrgs> rs = getRolestructure(); 
        for (RoleOrgs r : rs)
            if (r.getRole().equals(role)){
                rs.remove(r);
                break;
            }
        setRolestructure(rs);
    }
    
    public SortedSet<Organisation> getOrganisations(UserRole role){
        if (role.getKind_global()) return null;
        SortedSet<Organisation> set = new TreeSet<>();
        for (RoleOrgs ro : getRolestructure())
            if (ro.getRole().equals(role)) set.addAll(ro.getOrgs());
        return set;
    }
    
    public SortedSet<Organisation> getAllOrganisations(){
        SortedSet<Organisation> set = new TreeSet<>();
        for (RoleOrgs ro : getRolestructure())
            if (!ro.getRole().getKind_global())
                set.addAll(ro.getOrgs());
        return set;
    }
    
    public void addOrganisation(UserRole role, Organisation org){
        if (role.getKind_global()) return;
        SortedSet<RoleOrgs> rs = getRolestructure(); 
        for (RoleOrgs r : rs)
            if (r.getRole().equals(role))
                r.addOrg(org);
        setRolestructure(rs);
    }
    
    public void removeOrganisation(UserRole role, Organisation org){
        if (role.getKind_global()) return;
        SortedSet<RoleOrgs> rs = getRolestructure(); 
        for (RoleOrgs r : rs)
            if (r.getRole().equals(role))
                r.removeOrg(org);
        setRolestructure(rs);
    }
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Number id) {
        this.id = id!=null ? id.longValue() : null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Statics.firstUpperCase(name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = Statics.firstUpperCase(surname);
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = Statics.firstUpperCase(patronymic);
    }

    public Long getPhoto() {
        return photo;
    }

    public void setPhoto(Long photo) {
        this.photo = photo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login.toLowerCase();
    }

    public String getPasshash() {
        return passhash;
    }

    public void setPasshash(String passhash) {
        this.passhash = passhash;
    }

    public String getRolestring() {
        return rolestring;
    }

    public void setRolestring(String rolestring) {
        this.rolestring = rolestring;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserData)) {
            return false;
        }
        UserData other = (UserData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) return false;
        if ((this.surname == null && other.surname != null) || (this.surname != null && !this.surname.equals(other.surname))) return false;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) return false;
        if ((this.patronymic == null && other.patronymic != null) || (this.patronymic != null && !this.patronymic.equals(other.patronymic))) return false;
        return true;
    }
    
    @Override
    public int compareTo(UserData obj2) {
        if (!Objects.equals(this.active, obj2.getActive())) return this.active==null ? -1 : (obj2.getActive()==null ? 1 : -1*this.active.compareTo(obj2.getActive()));
        else if (!Objects.equals(this.surname, obj2.getSurname())) return this.surname==null ? -1 : (obj2.getSurname()==null ? 1 : this.surname.compareTo(obj2.getSurname()));
        else if (!Objects.equals(this.name, obj2.getName())) return this.name==null ? -1 : (obj2.getName()==null ? 1 : this.name.compareTo(obj2.getName()));
        else if (!Objects.equals(this.patronymic, obj2.getPatronymic())) return this.patronymic==null ? -1 : (obj2.getPatronymic()==null ? 1 : this.patronymic.compareTo(obj2.getPatronymic()));
        else return this.id.compareTo(obj2.getId());
    }

    @Override
    public String toString() {
        String d = Statics.delimiter_1;
        String s = "";
        if (id!=null) s=s+"ИД:"+id+d;
        if (name!=null) s=s+"Имя:"+name+d;
        if (surname!=null) s=s+"Фамилия:"+surname+d;
        if (patronymic!=null) s=s+"Отчество:"+patronymic+d;
        if (login!=null) s=s+"Имя пользователя:"+login+d;
        if (rolestring!=null) s=s+"Права:"+rolestring+d;
        return Statics.cropLastDelimiter(s,d);
    }
    
    public String getDifference(UserData obj2) {
        String d = Statics.delimiter_1;
        String s = "";
        if (!Objects.equals(id, obj2.getId())) s=s+"ИД:"+obj2.getId()+d;
        if (!Objects.equals(name, obj2.getName())) s=s+"Имя:"+obj2.getName()+d;
        if (!Objects.equals(surname, obj2.getSurname())) s=s+"Фамилия:"+obj2.getSurname()+d;
        if (!Objects.equals(patronymic, obj2.getPatronymic())) s=s+"Отчество:"+obj2.getPatronymic()+d;
        if (!Objects.equals(login, obj2.getLogin())) s=s+"Имя пользователя:"+obj2.getLogin()+d;
        if (!Objects.equals(rolestring, obj2.getRolestring())) s=s+"Права:"+obj2.getRolestring()+d;
        return Statics.cropLastDelimiter(s,d);
    }
    
    public Boolean hasRole (Short role){
        for (RoleOrgs ro : getRolestructure())
            if (ro.getRole().getId().equals(role)) return true;
        return false;
    }
    
}
