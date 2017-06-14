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
import javax.persistence.FetchType;
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
@Table(name = "FILTERS")
public class Filters implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FILTERID")
    private Long filterId;

   	@Column(name = "IPADDRESS", length = 20)
    private String ipAddress;

    @Column(name = "PACKAGEID", length = 50)
    private String packageId;

    @Column(name = "WATCHEDCHANNELID", length = 30)
    private String watchedChannelId;

    @Column(name = "LOCATIONID", length = 30)
    private String locationId;

    @Column(name = "SOFTWAREVERSION", length = 30)
    private String softwareVersion;

    @Column(name = "UIVERSION", length = 30)
    private String uiVersion;

    @Column(name = "CURRENTUILANGUAGE", length = 30)
    private String currentUiLanguage;

    @Column(name = "HARDWAREVERSION", length = 30)
    private String hardwareVersion;

    @Column(name = "FIPSCODE", length = 6)
    private String fipsCode;

    @JoinColumn(name = "MESSAGEID")
    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    private Messages message;

    /** Filters */
    public Filters() {
    }

    /**
     * getFilterId
     * @return the filterId
     */
    public Long getFilterId() {
        return filterId;
    }

    /**
     * setFilterId
     * @param filterId the filterId to set
     */
    public void setFilterId(Long filterId) {
        this.filterId = filterId;
    }

    /**
     * getIpAddress
     * @return the ipAddress
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * setIpAddress
     * @param ipAddress the ipAddress to set
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * getPackageId
     * @return the packageId
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * setPackageId
     * @param packageId the packageId to set
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    /**
     * getWatchedChannelId
     * @return the watchedChannelId
     */
    public String getWatchedChannelId() {
        return watchedChannelId;
    }

    /**
     * setWatchedChannelId
     * @param watchedChannelId the watchedChannelId to set
     */
    public void setWatchedChannelId(String watchedChannelId) {
        this.watchedChannelId = watchedChannelId;
    }

    /**
     * getLocationId
     * @return the locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * setLocationId
     * @param locationId the locationId to set
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * getSoftwareVersion
     * @return the softwareVersion
     */
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    /**
     * setSoftwareVersion
     * @param softwareVersion the softwareVersion to set
     */
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    /**
     * getUiVersion
     * @return the uiVersion
     */
    public String getUiVersion() {
        return uiVersion;
    }

    /**
     * setUiVersion
     * @param uiVersion the uiVersion to set
     */
    public void setUiVersion(String uiVersion) {
        this.uiVersion = uiVersion;
    }

    /**
     * getCurrentUiLanguage
     * @return the currentUiLanguage
     */
    public String getCurrentUiLanguage() {
        return currentUiLanguage;
    }

    /**
     *
     * setCurrentUiLanguage
     * @param currentUiLanguage the currentUiLanguage to set
     */
    public void setCurrentUiLanguage(String currentUiLanguage) {
        this.currentUiLanguage = currentUiLanguage;
    }

    /**
     *  getHardwareVersion
     * @return the hardwareVersion
     */
    public String getHardwareVersion() {
        return hardwareVersion;
    }

    /**
     * setHardwareVersion
     * @param hardwareVersion the hardwareVersion to set
     */
    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    /**
     * getFipsCode
     * @return the fipsCode
     */
    public String getFipsCode() {
        return fipsCode;
    }

    /**
     * setFipsCode
     * @param fipsCode the fipsCode to set
     */
    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (filterId != null ? filterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Filters)) {
            return false;
        }
        Filters other = (Filters) object;
        return !((this.filterId == null && other.filterId != null) || (this.filterId != null && !this.filterId.equals(other.filterId)));
    }

    @Override
    public String toString() {
        return "com.acn.avs.push.messaging.entity.Filters[ filterId=" + getFilterId() + " ]";
    }
    /**
     * getMessage
     * @return
     */
    public Messages getMessage() {
        return message;
    }

	/**
	 * setMessage
	 * @param message
	 */
	public void setMessage(Messages message) {
		this.message = message;
	}
}
