/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package advanced;

import boundary.OrganisationFacade;
import boundary.UserRoleFacade;
import entities.Organisation;
import entities.UserRole;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author k
 */
public class RoleOrgs implements Comparator<RoleOrgs>, Comparable<RoleOrgs>{
    UserRoleFacade userRoleFacade = lookupUserRoleFacadeBean();
    OrganisationFacade organisationFacade = lookupOrganisationFacadeBean();
    
    UserRole role;
    SortedSet<Organisation> orgs;
    
    public RoleOrgs(UserRole role){
        this.role = role;
        if (!role.getKind_global()) orgs = new TreeSet<>();
    }
    
    public RoleOrgs(String rolestring_part){
        if (!rolestring_part.contains(Statics.delimiter_2)){
            role = userRoleFacade.find(Short.parseShort(rolestring_part));
            if (!role.getKind_global()){
                orgs = new TreeSet<>();
            }
        }
        else {
            String[] bigArray = rolestring_part.split(Statics.delimiter_2);
            role = userRoleFacade.find(Short.parseShort(bigArray[0]));
            if (!role.getKind_global()){
                orgs = new TreeSet<>();
                for (String s : bigArray[1].split(Statics.delimiter_3))
                    orgs.add(organisationFacade.find(Long.parseLong(s)));
            }
        }
    }
    
    public String getOriginal(){
        if (role.getKind_global())
            return role.getId().toString();
        else{
            String s = "";
            s = s + role.getId().toString() + Statics.delimiter_2;
            for (Organisation org : orgs) s = s + org.getId().toString() + Statics.delimiter_3;
            return Statics.cropLastDelimiter(Statics.cropLastDelimiter(s, Statics.delimiter_3), Statics.delimiter_2);
        }
    }
    
    public void addOrg (Organisation org){
        orgs.add(org);
    }
    
    public void removeOrg (Organisation org){
        orgs.remove(org);
    }
    
    @Override
    public int compare(RoleOrgs obj1, RoleOrgs obj2) {
        if (!Objects.equals(obj1.role.getKind_global(), obj2.role.getKind_global())) return obj1.role.getKind_global()==null ? -1 : (obj2.role.getKind_global()==null ? 1 : -1*obj1.role.getKind_global().compareTo(obj2.role.getKind_global())) ;
        else if (!Objects.equals(obj1.role.getName(), obj2.role.getName())) return obj1.role.getName()==null ? -1 : (obj2.role.getName()==null ? 1 : obj1.role.getName().compareTo(obj2.role.getName())) ;
        else if (obj1.orgs!=null && obj2.orgs!=null) return Integer.signum(obj1.orgs.size()-obj2.orgs.size());
        else return 0;
    }
    
    @Override
    public int compareTo(RoleOrgs obj2) {
        if (!Objects.equals(this.role.getKind_global(), obj2.role.getKind_global())) return this.role.getKind_global()==null ? -1 : (obj2.role.getKind_global()==null ? 1 : -1*this.role.getKind_global().compareTo(obj2.role.getKind_global())) ;
        else if (!Objects.equals(this.role.getName(), obj2.role.getName())) return this.role.getName()==null ? -1 : (obj2.role.getName()==null ? 1 : this.role.getName().compareTo(obj2.role.getName())) ;
        else if (this.orgs!=null && obj2.orgs!=null) return Integer.signum(this.orgs.size()-obj2.orgs.size());
        else return 0;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Set<Organisation> getOrgs() {
        return orgs;
    }
    
    public Set<Long> getOrgs_ID(){
        Set<Long> set = new HashSet<>();
        orgs.stream().forEach((x) -> {
            set.add(x.getId());
        });
        return set;
    }

    public void setOrgs(SortedSet<Organisation> orgs) {
        this.orgs = orgs;
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

    private UserRoleFacade lookupUserRoleFacadeBean() {
        try {
            Context c = new InitialContext();
            return (UserRoleFacade) c.lookup("java:global/CommonPart/UserRoleFacade!boundary.UserRoleFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
}
