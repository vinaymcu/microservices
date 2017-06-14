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
import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author vinay.gupta
 */
@Entity
@Table(name = "MESSAGES")
@NamedQueries({
    @NamedQuery(name="DELETE_PAST_MESSAGES",
                query=" Delete from Messages where expirationDatetime <= :currTime")
})
public class Messages implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name="customGenerator", strategy="com.acn.avs.push.messaging.entity.CustomGenerator")
	@GeneratedValue(generator  = "customGenerator")
	@Column(name = "MESSAGEID", nullable = false)
	private Long messageId;

	@Column(name = "SUBSCRIBERID", length = 100)
	private String subscriberId;

	@Column(name = "MESSAGENAME", length = 75)
	private String messageName;

	@Column(name = "MULTICAST")
	private Boolean multiCast;

	@Column(name = "MESSAGEURL", length = 1000)
	private String messageUrl;

	@Column(name = "MESSAGETEXT", length = 1024)
	private String messageText;

	@Column(name = "DISPLAYDATETIME")
	private BigInteger displayDatetime;

	@Column(name = "IMMEDIATELY")
	private Boolean immediately;

	@Column(name = "AUTOHIDETIME")
	private BigInteger autohideTime;

	@Column(name = "EXPIRATIONDATETIME")
	private BigInteger expirationDatetime;

	@Column(name = "STATUS	")
	private Boolean status;

	@Column(name = "ACTIVATIONDATETIME")
	private BigInteger activationDatetime;

	@Column(name = "CREATIONDATETIME")
	private BigInteger creationDatetime;

	@Column(name = "MESSAGETYPE", length = 15)
	private String messageType;

	@Column(name = "POPUPMESSAGE")
	private Boolean popupMessage;

	@Column(name = "POPUPMESSAGETEXT", length = 1024)
	private String popupMessageText;

/*	@JoinColumn(name = "MESSAGEID", referencedColumnName = "MESSAGEID")
	@OneToMany(cascade = CascadeType.ALL)
	private List<StbAddressing> stbAddressingList;*/

	@OneToMany(mappedBy = "message", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StbAddressing> stbAddressingList;

//	@JoinColumn(name = "MESSAGEID", referencedColumnName = "MESSAGEID")
//	@OneToMany(cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "message", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Filters> filtersList;

	/**
	 * Messages
	 */
	public Messages() {
	}

	/**
	 *
	 * @param messageId
	 */
	public Messages(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 *
	 * @return messageId
	 */
	public Long getMessageId() {
		return messageId;
	}

	/**
	 *
	 * @param messageId
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 *
	 * @return subscriberId
	 */
	public String getSubscriberId() {
		return subscriberId;
	}

	/**
	 *
	 * @param subscriberId
	 */
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	/**
	 *
	 * @return messageName
	 */
	public String getMessageName() {
		return messageName;
	}

	/**
	 *
	 * @param messageName
	 */
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

    /**
     *
     * @return multiCast
     */
	public Boolean getMultiCast() {
		return multiCast;
	}

	/**
	 *
	 * @param multiCast
	 */
	public void setMultiCast(Boolean multiCast) {
		this.multiCast = multiCast;
	}

	/**
	 *
	 * @return messageUrl
	 */
	public String getMessageUrl() {
		return messageUrl;
	}

	/**
	 *
	 * @param messageUrl
	 */
	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}

	/**
	 *
	 * @return messageText
	 */
	public String getMessageText() {
		return messageText;
	}

	/**
	 *
	 * @param messageText
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	/**
	 *
	 * @return displayDatetime
	 */
	public BigInteger getDisplayDatetime() {
		return displayDatetime;
	}

	/**
	 *
	 * @param displayDatetime
	 */
	public void setDisplayDatetime(BigInteger displayDatetime) {
		this.displayDatetime = displayDatetime;
	}

    /**
     *
     * @return immediately
     */
	public Boolean getImmediately() {
		return immediately;
	}

	/**
	 *
	 * @param immediately
	 */
	public void setImmediately(Boolean immediately) {
		this.immediately = immediately;
	}

	/**
	 *
	 * @return autohideTime
	 */
	public BigInteger getAutohideTime() {
		return autohideTime;
	}

	/**
	 *
	 * @param autohideTime
	 */
	public void setAutohideTime(BigInteger autohideTime) {
		this.autohideTime = autohideTime;
	}

	/**
	 *
	 * @return expirationDatetime
	 */
	public BigInteger getExpirationDatetime() {
		return expirationDatetime;
	}

	/**
	 *
	 * @param expirationDatetime
	 */
	public void setExpirationDatetime(BigInteger expirationDatetime) {
		this.expirationDatetime = expirationDatetime;
	}
    /**
     *
     * @return status
     */
	public Boolean getStatus() {
		return status;
	}
    /**
     *
     * @param status
     */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	/**
	 *
	 * @return activationDatetime
	 */
	public BigInteger getActivationDatetime() {
		return activationDatetime;
	}

	/**
	 *
	 * @param activationDatetime
	 */
	public void setActivationDatetime(BigInteger activationDatetime) {
		this.activationDatetime = activationDatetime;
	}

	/**
	 *
	 * @return creationDatetime
	 */
	public BigInteger getCreationDatetime() {
		return creationDatetime;
	}

	/**
	 *
	 * @param creationDatetime
	 */
	public void setCreationDatetime(BigInteger creationDatetime) {
		this.creationDatetime = creationDatetime;
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

	/**
	 *
	 * @return popupMessage
	 */
	public Boolean getPopupMessage() {
		return popupMessage;
	}

	/**
	 *
	 * @param popupMessage
	 */
	public void setPopupMessage(Boolean popupMessage) {
		this.popupMessage = popupMessage;
	}

	/**
	 *
	 * @return popupMessageText
	 */
	public String getPopupMessageText() {
		return popupMessageText;
	}

	/**
	 *
	 * @param popupMessageText
	 */
	public void setPopupMessageText(String popupMessageText) {
		this.popupMessageText = popupMessageText;
	}

	/**
	 *
	 * @return stbAddressingList
	 */
	public List<StbAddressing> getStbAddressingList() {
		return stbAddressingList;
	}

	/**
	 *
	 * @param stbAddressingList
	 */
	public void setStbAddressingList(List<StbAddressing> stbAddressingList) {
		this.stbAddressingList = stbAddressingList;
	}

	/**
	 *
	 * @return filtersList
	 */
	public List<Filters> getFiltersList() {
		return filtersList;
	}

	/**
	 *
	 * @param filtersList
	 */
	public void setFiltersList(List<Filters> filtersList) {
		this.filtersList = filtersList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (messageId != null ? messageId.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Messages)) {
			return false;
		}
		Messages other = (Messages) object;
		if ((this.messageId == null && other.messageId != null)
				|| (this.messageId != null && !this.messageId.equals(other.messageId))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.acn.avs.push.messaging.entity.Messages[ messageId=" + messageId + " ]";
	}
}