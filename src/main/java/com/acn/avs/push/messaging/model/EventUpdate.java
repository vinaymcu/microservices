
package com.acn.avs.push.messaging.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    "TriggerType",
    "SubscriberId",
    "MACAddress",
    "TriggerInfo",
    "Timestamp"
})
public class EventUpdate implements Serializable
{

    /**
     * Trigger Type like T_SUBSCRIBER_INFO, T_FAVORITES etc
     * (Required)
     * 
     */
    @JsonProperty("TriggerType")
    @JsonPropertyDescription("")
    private String triggerType;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("SubscriberId")
    private String subscriberId;
    @JsonProperty("MACAddress")
    private List<String> mACAddress = new ArrayList<String>();
    /**
     * Additional inforamtion requied for trigger like message id
     * 
     */
    @JsonProperty("TriggerInfo")
    @JsonPropertyDescription("")
    private String triggerInfo;
    /**
     * Epoch Time of notification. this is required to send trigger to TQS server
     * (Required)
     * 
     */
    @JsonProperty("Timestamp")
    @JsonPropertyDescription("")
    private String timestamp;
    private final static long serialVersionUID = 1660804453334636801L;

    /**
     * Trigger Type like T_SUBSCRIBER_INFO, T_FAVORITES etc
     * (Required)
     * 
     * @return
     *     The triggerType
     */
    @JsonProperty("TriggerType")
    public String getTriggerType() {
        return triggerType;
    }

    /**
     * Trigger Type like T_SUBSCRIBER_INFO, T_FAVORITES etc
     * (Required)
     * 
     * @param triggerType
     *     The TriggerType
     */
    @JsonProperty("TriggerType")
    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public EventUpdate withTriggerType(String triggerType) {
        this.triggerType = triggerType;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The subscriberId
     */
    @JsonProperty("SubscriberId")
    public String getSubscriberId() {
        return subscriberId;
    }

    /**
     * 
     * (Required)
     * 
     * @param subscriberId
     *     The SubscriberId
     */
    @JsonProperty("SubscriberId")
    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public EventUpdate withSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    /**
     * 
     * @return
     *     The mACAddress
     */
    @JsonProperty("MACAddress")
    public List<String> getMACAddress() {
        return mACAddress;
    }

    /**
     * 
     * @param mACAddress
     *     The MACAddress
     */
    @JsonProperty("MACAddress")
    public void setMACAddress(List<String> mACAddress) {
        this.mACAddress = mACAddress;
    }

    public EventUpdate withMACAddress(List<String> mACAddress) {
        this.mACAddress = mACAddress;
        return this;
    }

    /**
     * Additional inforamtion requied for trigger like message id
     * 
     * @return
     *     The triggerInfo
     */
    @JsonProperty("TriggerInfo")
    public String getTriggerInfo() {
        return triggerInfo;
    }

    /**
     * Additional inforamtion requied for trigger like message id
     * 
     * @param triggerInfo
     *     The TriggerInfo
     */
    @JsonProperty("TriggerInfo")
    public void setTriggerInfo(String triggerInfo) {
        this.triggerInfo = triggerInfo;
    }

    public EventUpdate withTriggerInfo(String triggerInfo) {
        this.triggerInfo = triggerInfo;
        return this;
    }

    /**
     * Epoch Time of notification. this is required to send trigger to TQS server
     * (Required)
     * 
     * @return
     *     The timestamp
     */
    @JsonProperty("Timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Epoch Time of notification. this is required to send trigger to TQS server
     * (Required)
     * 
     * @param timestamp
     *     The Timestamp
     */
    @JsonProperty("Timestamp")
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public EventUpdate withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(triggerType).append(subscriberId).append(mACAddress).append(triggerInfo).append(timestamp).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EventUpdate) == false) {
            return false;
        }
        EventUpdate rhs = ((EventUpdate) other);
        return new EqualsBuilder().append(triggerType, rhs.triggerType).append(subscriberId, rhs.subscriberId).append(mACAddress, rhs.mACAddress).append(triggerInfo, rhs.triggerInfo).append(timestamp, rhs.timestamp).isEquals();
    }

}
