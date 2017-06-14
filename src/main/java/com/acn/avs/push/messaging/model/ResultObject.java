
package com.acn.avs.push.messaging.model;

import java.io.Serializable;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "resultCode",
    "resultDescription",
    "id",
    "UnicastMessage",
    "MulticastMessage",
    "UnicastMessageAssociation",
    "MulticastMessageFilter"
})
public class ResultObject implements Serializable
{

    /**
     * ACN_200 in case of success response, ACN_XXXX in case of error
     * (Required)
     * 
     */
    @JsonProperty("resultCode")
    @JsonPropertyDescription("")
    private Object resultCode;
    /**
     * OK if resultCode is ACN_200, otherwise it will be the description of the error.
     * (Required)
     * 
     */
    @JsonProperty("resultDescription")
    @JsonPropertyDescription("")
    private Object resultDescription;
    /**
     * Id of the message
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("")
    private String id;
    /**
     * 
     */
    @JsonProperty("UnicastMessage")
    private UnicastMessage unicastMessage;
    /**
     * 
     */
    @JsonProperty("MulticastMessage")
    private MulticastMessage multicastMessage;
    /**
     * 
     */
    @JsonProperty("UnicastMessageAssociation")
    private UnicastMessageAssociation unicastMessageAssociation;
    /**
     * 
     */
    @JsonProperty("MulticastMessageFilter")
    private MulticastMessageFilter multicastMessageFilter;
    private final static long serialVersionUID = -1409301499885399114L;

    /**
     * ACN_200 in case of success response, ACN_XXXX in case of error
     * (Required)
     * 
     * @return
     *     The resultCode
     */
    @JsonProperty("resultCode")
    public Object getResultCode() {
        return resultCode;
    }

    /**
     * ACN_200 in case of success response, ACN_XXXX in case of error
     * (Required)
     * 
     * @param resultCode
     *     The resultCode
     */
    @JsonProperty("resultCode")
    public void setResultCode(Object resultCode) {
        this.resultCode = resultCode;
    }

    public ResultObject withResultCode(Object resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    /**
     * OK if resultCode is ACN_200, otherwise it will be the description of the error.
     * (Required)
     * 
     * @return
     *     The resultDescription
     */
    @JsonProperty("resultDescription")
    public Object getResultDescription() {
        return resultDescription;
    }

    /**
     * OK if resultCode is ACN_200, otherwise it will be the description of the error.
     * (Required)
     * 
     * @param resultDescription
     *     The resultDescription
     */
    @JsonProperty("resultDescription")
    public void setResultDescription(Object resultDescription) {
        this.resultDescription = resultDescription;
    }

    public ResultObject withResultDescription(Object resultDescription) {
        this.resultDescription = resultDescription;
        return this;
    }

    /**
     * Id of the message
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Id of the message
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ResultObject withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The unicastMessage
     */
    @JsonProperty("UnicastMessage")
    public UnicastMessage getUnicastMessage() {
        return unicastMessage;
    }

    /**
     * 
     * @param unicastMessage
     *     The UnicastMessage
     */
    @JsonProperty("UnicastMessage")
    public void setUnicastMessage(UnicastMessage unicastMessage) {
        this.unicastMessage = unicastMessage;
    }

    public ResultObject withUnicastMessage(UnicastMessage unicastMessage) {
        this.unicastMessage = unicastMessage;
        return this;
    }

    /**
     * 
     * @return
     *     The multicastMessage
     */
    @JsonProperty("MulticastMessage")
    public MulticastMessage getMulticastMessage() {
        return multicastMessage;
    }

    /**
     * 
     * @param multicastMessage
     *     The MulticastMessage
     */
    @JsonProperty("MulticastMessage")
    public void setMulticastMessage(MulticastMessage multicastMessage) {
        this.multicastMessage = multicastMessage;
    }

    public ResultObject withMulticastMessage(MulticastMessage multicastMessage) {
        this.multicastMessage = multicastMessage;
        return this;
    }

    /**
     * 
     * @return
     *     The unicastMessageAssociation
     */
    @JsonProperty("UnicastMessageAssociation")
    public UnicastMessageAssociation getUnicastMessageAssociation() {
        return unicastMessageAssociation;
    }

    /**
     * 
     * @param unicastMessageAssociation
     *     The UnicastMessageAssociation
     */
    @JsonProperty("UnicastMessageAssociation")
    public void setUnicastMessageAssociation(UnicastMessageAssociation unicastMessageAssociation) {
        this.unicastMessageAssociation = unicastMessageAssociation;
    }

    public ResultObject withUnicastMessageAssociation(UnicastMessageAssociation unicastMessageAssociation) {
        this.unicastMessageAssociation = unicastMessageAssociation;
        return this;
    }

    /**
     * 
     * @return
     *     The multicastMessageFilter
     */
    @JsonProperty("MulticastMessageFilter")
    public MulticastMessageFilter getMulticastMessageFilter() {
        return multicastMessageFilter;
    }

    /**
     * 
     * @param multicastMessageFilter
     *     The MulticastMessageFilter
     */
    @JsonProperty("MulticastMessageFilter")
    public void setMulticastMessageFilter(MulticastMessageFilter multicastMessageFilter) {
        this.multicastMessageFilter = multicastMessageFilter;
    }

    public ResultObject withMulticastMessageFilter(MulticastMessageFilter multicastMessageFilter) {
        this.multicastMessageFilter = multicastMessageFilter;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(resultCode).append(resultDescription).append(id).append(unicastMessage).append(multicastMessage).append(unicastMessageAssociation).append(multicastMessageFilter).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ResultObject) == false) {
            return false;
        }
        ResultObject rhs = ((ResultObject) other);
        return new EqualsBuilder().append(resultCode, rhs.resultCode).append(resultDescription, rhs.resultDescription).append(id, rhs.id).append(unicastMessage, rhs.unicastMessage).append(multicastMessage, rhs.multicastMessage).append(unicastMessageAssociation, rhs.unicastMessageAssociation).append(multicastMessageFilter, rhs.multicastMessageFilter).isEquals();
    }

}
