/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import advanced.Statics;
import java.io.Serializable;
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
@Table(name = "filesmeta")
@NamedQueries({
    @NamedQuery(name = "Filesmeta.findAll", query = "SELECT f FROM Filesmeta f")})
public class Filesmeta implements Serializable {
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
    @Column(name = "contenttype")
    private String contenttype;
    @Column(name = "link2data")
    private Long link2data;

    public Filesmeta() {
    }

    public Filesmeta(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public Long getLink2data() {
        return link2data;
    }

    public void setLink2data(Long link2data) {
        this.link2data = link2data;
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
        if (!(object instanceof Filesmeta)) {
            return false;
        }
        Filesmeta other = (Filesmeta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) return false;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) return false;
        if ((this.contenttype == null && other.contenttype != null) || (this.contenttype != null && !this.contenttype.equals(other.contenttype))) return false;
        if ((this.link2data == null && other.link2data != null) || (this.link2data != null && !this.link2data.equals(other.link2data))) return false;
        return true;
    }

    @Override
    public String toString() {
        String d = Statics.delimiter_1;
        StringBuilder s = new StringBuilder();
        if (id!=null) {s.append("ИД:"); s.append(id); s.append(d);}
        if (id!=null) {s.append("Имя файла:"); s.append(name); s.append(d);}
        if (id!=null) {s.append("Вид содержимого:"); s.append(contenttype); s.append(d);}
        if (id!=null) {s.append("Ссылка:"); s.append(link2data); s.append(d);}
        return Statics.cropLastDelimiter(s.toString(), d);
    }
    
}
