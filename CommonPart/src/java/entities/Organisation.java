/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import advanced.Statics;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author k
 */
@Entity
@Table(name = "organisation")
public class Organisation extends AbstractEntity implements Serializable, Comparator<Organisation>, Comparable<Organisation> {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "ogrn")
    private Long ogrn;
    @Size(max = 2147483647)
    @Column(name = "legaladdress")
    private String legaladdress;

    public Organisation() {
    }

    public Organisation(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Number id) {
        this.id = id.longValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Statics.firstUpperCase(name);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getOgrn() {
        return ogrn;
    }

    public void setOgrn(Long ogrn) {
        this.ogrn = ogrn;
    }

    public String getLegaladdress() {
        return legaladdress;
    }

    public void setLegaladdress(String legaladdress) {
        this.legaladdress = legaladdress;
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
        if (!(object instanceof Organisation)) {
            return false;
        }
        Organisation other = (Organisation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) return false;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) return false;
        if ((this.active == null && other.active != null) || (this.active != null && !this.active.equals(other.active))) return false;
        if ((this.legaladdress == null && other.legaladdress != null) || (this.legaladdress != null && !this.legaladdress.equals(other.legaladdress))) return false;
        if ((this.ogrn == null && other.ogrn != null) || (this.ogrn != null && !this.ogrn.equals(other.ogrn))) return false;
        return true;
    }

    @Override
    public int compare(Organisation obj1, Organisation obj2) {
        if (!Objects.equals(obj1.active, obj2.active)) return obj1.active==null ? -1 : (obj2.active==null ? 1 : -1*obj1.active.compareTo(obj2.active)) ;
        else if (!Objects.equals(obj1.name, obj2.name)) return obj1.name==null ? -1 : (obj2.name==null ? 1 : obj1.name.compareTo(obj2.name)) ;
        else if (!Objects.equals(obj1.ogrn, obj2.ogrn)) return obj1.ogrn==null ? -1 : (obj2.ogrn==null ? 1 : obj1.ogrn.compareTo(obj2.ogrn)) ;
        else if (!Objects.equals(obj1.legaladdress, obj2.legaladdress)) return obj1.legaladdress==null ? -1 : (obj2.legaladdress==null ? 1 : obj1.legaladdress.compareTo(obj2.legaladdress)) ;
        else return obj1.id.compareTo(obj2.id);
    }
    
    @Override
    public int compareTo(Organisation obj2) {
        if (!Objects.equals(this.active, obj2.active)) return this.active==null ? -1 : (obj2.active==null ? 1 : -1*this.active.compareTo(obj2.active)) ;
        else if (!Objects.equals(this.name, obj2.name)) return this.name==null ? -1 : (obj2.name==null ? 1 : this.name.compareTo(obj2.name)) ;
        else if (!Objects.equals(this.ogrn, obj2.ogrn)) return this.ogrn==null ? -1 : (obj2.ogrn==null ? 1 : this.ogrn.compareTo(obj2.ogrn)) ;
        else if (!Objects.equals(this.legaladdress, obj2.legaladdress)) return this.legaladdress==null ? -1 : (obj2.legaladdress==null ? 1 : this.legaladdress.compareTo(obj2.legaladdress)) ;
        else return this.id.compareTo(obj2.id);
    }

    @Override
    public String toString() {
        String d = Statics.delimiter_1;
        String s = "";
        if (id!=null) s=s+"ИД:"+id+d;
        if (name!=null) s=s+"Наименование:"+name+d;
        return Statics.cropLastDelimiter(s,d);
    }
    
    public String getDifference(Organisation obj2) {
        String d = Statics.delimiter_1;
        String s = "";
        if (!Objects.equals(id, obj2.getId())) s=s+"ИД:"+obj2.getId()+d;
        if (!Objects.equals(name, obj2.getName())) s=s+"Наименование:"+obj2.getName()+d;
        return Statics.cropLastDelimiter(s,d);
    }
    
}
