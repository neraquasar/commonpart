/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import advanced.Statics;
import java.io.Serializable;
import java.util.Arrays;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "filesdata")
@NamedQueries({
    @NamedQuery(name = "Filesdata.findAll", query = "SELECT f FROM Filesdata f")})
public class Filesdata implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    @Column(name = "bytes")
    private byte[] bytes;
    @Size(max = 2147483647)
    @Column(name = "hash")
    private String hash;

    public Filesdata() {
    }

    public Filesdata(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
        this.hash = Statics.calculateHashString(Arrays.toString(bytes));
    }

    public String getHash() {
        return hash;
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
        if (!(object instanceof Filesdata)) {
            return false;
        }
        Filesdata other = (Filesdata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) return false;
        if ((this.hash == null && other.hash != null) || (this.hash != null && !this.hash.equals(other.hash))) return false;
        if ((this.bytes == null && other.bytes != null) || (this.bytes != null && !this.bytes.equals(other.bytes))) return false;
        return true;
    }

    @Override
    public String toString() {
        return "entities.Filesdata[ id=" + id + " ]";
    }
    
}
