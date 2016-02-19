/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "userrole")
@NamedQueries({
    @NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")})
public class UserRole extends AbstractEntity implements Serializable, Comparable<UserRole> {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Short id;
    @Column(name = "active")
    private Boolean active;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Column(name = "kind_global")
    private Boolean kind_global;

    public UserRole() {
    }

    public UserRole(Short id) {
        this.id = id;
    }

    @Override
    public Short getId() {
        return id;
    }

    @Override
    public void setId(Number id) {
        this.id = id!=null ? id.shortValue() : null;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getKind_global() {
        return kind_global;
    }

    public void setKind_global(Boolean kind_global) {
        this.kind_global = kind_global;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRole)) {
            return false;
        }
        UserRole other = (UserRole) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserRole[ id=" + id + " ]";
    }

    @Override
    public int compareTo(UserRole obj2) {
        if (!Objects.equals(this.name, obj2.getName())) return this.name==null ? -1 : (obj2.getName()==null ? 1 : -1*this.name.compareTo(obj2.getName())) ;
        else return Integer.signum(this.id-obj2.getId());
    }
    
}
