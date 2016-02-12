/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import advanced.Statics;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author k
 */
@Entity
@Table(name = "logs")
@NamedQueries({
    @NamedQuery(name = "Logs.findAll", query = "SELECT l FROM Logs l")})
public class Logs extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "entity")
    private Short entity;
    @Column(name = "idofinstance")
    private Long idofinstance;
    @Column(name = "action")
    private Short action;
    @Column(name = "timeofaction")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeofaction;
    @Column(name = "idofuser")
    private Long idofuser;
    @Size(max = 2147483647)
    @Column(name = "field_prewcontent")
    private String fieldPrewcontent;

    public Logs() {
    }

    public Logs(Short entity, Long idofinstance, Short action, Date timeofaction, Long idofuser) {
        this.entity = entity;
        this.idofinstance = idofinstance;
        this.action = action;
        this.timeofaction = Statics.timeCorrect(timeofaction);
        this.idofuser = idofuser;
    }
    
    public Logs(Short entity, Long idofinstance, Short action, Date timeofaction, Long idofuser, String fieldPrewcontent) {
        this.entity = entity;
        this.idofinstance = idofinstance;
        this.action = action;
        this.timeofaction = Statics.timeCorrect(timeofaction);
        this.idofuser = idofuser;
        this.fieldPrewcontent = fieldPrewcontent;
    }

    public Logs(Long id) {
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

    public Short getEntity() {
        return entity;
    }

    public void setEntity(Short entity) {
        this.entity = entity;
    }

    public Long getIdofinstance() {
        return idofinstance;
    }

    public void setIdofinstance(Long idofinstance) {
        this.idofinstance = idofinstance;
    }

    public Short getAction() {
        return action;
    }

    public void setAction(Short action) {
        this.action = action;
    }

    public Date getTimeofaction() {
        return timeofaction;
    }

    public void setTimeofaction(Date timeofaction) {
        this.timeofaction = timeofaction;
    }

    public Long getIdofuser() {
        return idofuser;
    }

    public void setIdofuser(Long idofuser) {
        this.idofuser = idofuser;
    }

    public String getFieldPrewcontent() {
        return fieldPrewcontent;
    }

    public void setFieldPrewcontent(String fieldPrewcontent) {
        this.fieldPrewcontent = fieldPrewcontent;
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
        if (!(object instanceof Logs)) {
            return false;
        }
        Logs other = (Logs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Logs[ id=" + id + " ]";
    }
    
}
