
package com.acn.avs.push.messaging.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "MessageId",
    "MessageName",
    "DistributionMode",
    "CreationDate",
    "ActivationDate",
    "ExprireDate",
    "Status"
})
public class MessageList implements Serializable
{

    /**
     * Id of the message.
     * (Required)
     * 
     */
    @JsonProperty("MessageId")
    @JsonPropertyDescription("")
    private Integer messageId;
    /**
     * Name of the message
     * (Required)
     * 
     */
    @JsonProperty("MessageName")
    @JsonPropertyDescription("")
    private String messageName;
    /**
     * Distribure mode of created message
     * (Required)
     * 
     */
    @JsonProperty("DistributionMode")
    @JsonPropertyDescription("")
    private MessageList.DistributionMode distributionMode;
    /**
     * Creation time of the message. This is read-only. System will generate the creation time.
     * (Required)
     * 
     */
    @JsonProperty("CreationDate")
    @JsonPropertyDescription("")
    private String creationDate;
    /**
     * Desired time and date, when message should be displayed.
     * (Required)
     * 
     */
    @JsonProperty("ActivationDate")
    @JsonPropertyDescription("")
    private String activationDate;
    /**
     * Date and time after which display of message is no longer needed.
     * (Required)
     * 
     */
    @JsonProperty("ExprireDate")
    @JsonPropertyDescription("")
    private String exprireDate;
    /**
     * Status of the message
     * (Required)
     * 
     */
    @JsonProperty("Status")
    @JsonPropertyDescription("")
    private String status;
    private final static long serialVersionUID = -2245547226134915772L;

    /**
     * Id of the message.
     * (Required)
     * 
     * @return
     *     The messageId
     */
    @JsonProperty("MessageId")
    public Integer getMessageId() {
        return messageId;
    }

    /**
     * Id of the message.
     * (Required)
     * 
     * @param messageId
     *     The MessageId
     */
    @JsonProperty("MessageId")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public MessageList withMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    /**
     * Name of the message
     * (Required)
     * 
     * @return
     *     The messageName
     */
    @JsonProperty("MessageName")
    public String getMessageName() {
        return messageName;
    }

    /**
     * Name of the message
     * (Required)
     * 
     * @param messageName
     *     The MessageName
     */
    @JsonProperty("MessageName")
    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public MessageList withMessageName(String messageName) {
        this.messageName = messageName;
        return this;
    }

    /**
     * Distribure mode of created message
     * (Required)
     * 
     * @return
     *     The distributionMode
     */
    @JsonProperty("DistributionMode")
    public MessageList.DistributionMode getDistributionMode() {
        return distributionMode;
    }

    /**
     * Distribure mode of created message
     * (Required)
     * 
     * @param distributionMode
     *     The DistributionMode
     */
    @JsonProperty("DistributionMode")
    public void setDistributionMode(MessageList.DistributionMode distributionMode) {
        this.distributionMode = distributionMode;
    }

    public MessageList withDistributionMode(MessageList.DistributionMode distributionMode) {
        this.distributionMode = distributionMode;
        return this;
    }

    /**
     * Creation time of the message. This is read-only. System will generate the creation time.
     * (Required)
     * 
     * @return
     *     The creationDate
     */
    @JsonProperty("CreationDate")
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Creation time of the message. This is read-only. System will generate the creation time.
     * (Required)
     * 
     * @param creationDate
     *     The CreationDate
     */
    @JsonProperty("CreationDate")
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public MessageList withCreationDate(String creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * Desired time and date, when message should be displayed.
     * (Required)
     * 
     * @return
     *     The activationDate
     */
    @JsonProperty("ActivationDate")
    public String getActivationDate() {
        return activationDate;
    }

    /**
     * Desired time and date, when message should be displayed.
     * (Required)
     * 
     * @param activationDate
     *     The ActivationDate
     */
    @JsonProperty("ActivationDate")
    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    public MessageList withActivationDate(String activationDate) {
        this.activationDate = activationDate;
        return this;
    }

    /**
     * Date and time after which display of message is no longer needed.
     * (Required)
     * 
     * @return
     *     The exprireDate
     */
    @JsonProperty("ExprireDate")
    public String getExprireDate() {
        return exprireDate;
    }

    /**
     * Date and time after which display of message is no longer needed.
     * (Required)
     * 
     * @param exprireDate
     *     The ExprireDate
     */
    @JsonProperty("ExprireDate")
    public void setExprireDate(String exprireDate) {
        this.exprireDate = exprireDate;
    }

    public MessageList withExprireDate(String exprireDate) {
        this.exprireDate = exprireDate;
        return this;
    }

    /**
     * Status of the message
     * (Required)
     * 
     * @return
     *     The status
     */
    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    /**
     * Status of the message
     * (Required)
     * 
     * @param status
     *     The Status
     */
    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    public MessageList withStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageId).append(messageName).append(distributionMode).append(creationDate).append(activationDate).append(exprireDate).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MessageList) == false) {
            return false;
        }
        MessageList rhs = ((MessageList) other);
        return new EqualsBuilder().append(messageId, rhs.messageId).append(messageName, rhs.messageName).append(distributionMode, rhs.distributionMode).append(creationDate, rhs.creationDate).append(activationDate, rhs.activationDate).append(exprireDate, rhs.exprireDate).append(status, rhs.status).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public enum DistributionMode {

        MULTICAST("Multicast"),
        BROADCAST("Broadcast"),
        UNICAST("Unicast");
        private final String value;
        private final static Map<String, MessageList.DistributionMode> CONSTANTS = new HashMap<String, MessageList.DistributionMode>();

        static {
            for (MessageList.DistributionMode c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private DistributionMode(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static MessageList.DistributionMode fromValue(String value) {
            MessageList.DistributionMode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
