
package com.acn.avs.push.messaging.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author vinay.gupta
 */
@Entity
@Table(name = "MESSAGETYPES")
public class MessageTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private Integer id;
   
    @Column(name = "MESSAGETYPE", length = 30)
    private String messageType;

    /**
     * MessageTypes
     */
    public MessageTypes() {
    }

    /**
     * 
     * @param id
     */
    public MessageTypes(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * 
     * @param messageType
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
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
        if (!(object instanceof MessageTypes)) {
            return false;
        }
        MessageTypes other = (MessageTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acn.avs.push.messaging.entity.MessageTypes[ id=" + id + " ]";
    }
}
