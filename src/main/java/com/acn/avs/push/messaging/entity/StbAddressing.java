/***************************************************************************
 * Copyright (C) Accenture
 * 
 * The reproduction, transmission or use of this document or its contents is not permitted without
 * prior express written consent of Accenture. Offenders will be liable for damages. All rights,
 * including but not limited to rights created by patent grant or registration of a utility model or
 * design, are reserved.
 * 
 * Accenture reserves the right to modify technical specifications and features.
 * 
 * Technical specifications and features are binding only insofar as they are specifically and
 * expressly agreed upon in a written contract.
 * 
 **************************************************************************/

package com.acn.avs.push.messaging.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author vinay.gupta
 */
@Entity
@Table(name = "STBADDRESSING")
public class StbAddressing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(nullable = false)
    private Integer id;

    @Column(name = "MACADDRESS", length = 30)
    private String macAddress;

    @Column(name = "MESSAGESTATUS")
    private Boolean messageStatus;

    @JoinColumn(name = "MESSAGEID")
    @ManyToOne
    private Messages message;

    /**
     * StbAddressing
     */
    public StbAddressing() {
    }

    /**
     * 
     * @param id
     */
    public StbAddressing(Integer id) {
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
     * @return macAddress
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * 
     * @param macAddress
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * 
     * @return messageStatus
     */
    public Boolean getMessageStatus() {
        return messageStatus;
    }

    /**
     * 
     * @param messageStatus
     */
    public void setMessagestatus(Boolean messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * 
     * @return message
     */
    public Messages getMessage() {
        return message;
    }

    /**
     * 
     * @param messageid
     */
    public void setMessage(Messages messageid) {
        this.message = messageid;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StbAddressing)) {
            return false;
        }
        StbAddressing other = (StbAddressing) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acn.avs.push.messaging.entity.StbAddressing[ id=" + id + " ]";
    }
}
