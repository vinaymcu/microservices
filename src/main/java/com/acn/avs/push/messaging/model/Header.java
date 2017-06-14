
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
    "IsPopupMessage",
    "MessageType",
    "CreationDateTime",
    "DisplayDateTime",
    "ValidityDateTime",
    "AutoHideTime",
    "Status"
})
public class Header implements Serializable
{

    /**
     * Id of the message.
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
    private Header.DistributionMode distributionMode;
    /**
     * The message flag to indicate if the message is a Popup. Default value is false.
     * (Required)
     * 
     */
    @JsonProperty("IsPopupMessage")
    @JsonPropertyDescription("")
    private Boolean isPopupMessage;
    /**
     * Type of Message. It can be Product, Service or CTA
     * (Required)
     * 
     */
    @JsonProperty("MessageType")
    @JsonPropertyDescription("")
    private Header.MessageType messageType;
    /**
     * Creation time of the message. This is read-only. System will generate the creation time
     * 
     */
    @JsonProperty("CreationDateTime")
    @JsonPropertyDescription("")
    private String creationDateTime;
    /**
     * Desired time and date, when message should be displayed. Default value will be immediately
     * 
     */
    @JsonProperty("DisplayDateTime")
    @JsonPropertyDescription("")
    private String displayDateTime;
    /**
     * Date and time after which display of message is no longer needed
     * (Required)
     * 
     */
    @JsonProperty("ValidityDateTime")
    @JsonPropertyDescription("")
    private String validityDateTime;
    /**
     *  Time (In seconds) a message is displayed before automatically being dismissed.  Value of 0 would mean subscriber must explicitly dismiss the message with the dismiss button. Autohide time only applies to  plain text messages, not to URL based messages.
     * (Required)
     * 
     */
    @JsonProperty("AutoHideTime")
    @JsonPropertyDescription("")
    private Integer autoHideTime;
    /**
     * Status of message. Default value is Inactive.
     * 
     */
    @JsonProperty("Status")
    @JsonPropertyDescription("")
    private String status;
    private final static long serialVersionUID = 8775453088604844886L;

    /**
     * Id of the message.
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
     * 
     * @param messageId
     *     The MessageId
     */
    @JsonProperty("MessageId")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Header withMessageId(Integer messageId) {
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

    public Header withMessageName(String messageName) {
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
    public Header.DistributionMode getDistributionMode() {
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
    public void setDistributionMode(Header.DistributionMode distributionMode) {
        this.distributionMode = distributionMode;
    }

    public Header withDistributionMode(Header.DistributionMode distributionMode) {
        this.distributionMode = distributionMode;
        return this;
    }

    /**
     * The message flag to indicate if the message is a Popup. Default value is false.
     * (Required)
     * 
     * @return
     *     The isPopupMessage
     */
    @JsonProperty("IsPopupMessage")
    public Boolean getIsPopupMessage() {
        return isPopupMessage;
    }

    /**
     * The message flag to indicate if the message is a Popup. Default value is false.
     * (Required)
     * 
     * @param isPopupMessage
     *     The IsPopupMessage
     */
    @JsonProperty("IsPopupMessage")
    public void setIsPopupMessage(Boolean isPopupMessage) {
        this.isPopupMessage = isPopupMessage;
    }

    public Header withIsPopupMessage(Boolean isPopupMessage) {
        this.isPopupMessage = isPopupMessage;
        return this;
    }

    /**
     * Type of Message. It can be Product, Service or CTA
     * (Required)
     * 
     * @return
     *     The messageType
     */
    @JsonProperty("MessageType")
    public Header.MessageType getMessageType() {
        return messageType;
    }

    /**
     * Type of Message. It can be Product, Service or CTA
     * (Required)
     * 
     * @param messageType
     *     The MessageType
     */
    @JsonProperty("MessageType")
    public void setMessageType(Header.MessageType messageType) {
        this.messageType = messageType;
    }

    public Header withMessageType(Header.MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    /**
     * Creation time of the message. This is read-only. System will generate the creation time
     * 
     * @return
     *     The creationDateTime
     */
    @JsonProperty("CreationDateTime")
    public String getCreationDateTime() {
        return creationDateTime;
    }

    /**
     * Creation time of the message. This is read-only. System will generate the creation time
     * 
     * @param creationDateTime
     *     The CreationDateTime
     */
    @JsonProperty("CreationDateTime")
    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Header withCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
        return this;
    }

    /**
     * Desired time and date, when message should be displayed. Default value will be immediately
     * 
     * @return
     *     The displayDateTime
     */
    @JsonProperty("DisplayDateTime")
    public String getDisplayDateTime() {
        return displayDateTime;
    }

    /**
     * Desired time and date, when message should be displayed. Default value will be immediately
     * 
     * @param displayDateTime
     *     The DisplayDateTime
     */
    @JsonProperty("DisplayDateTime")
    public void setDisplayDateTime(String displayDateTime) {
        this.displayDateTime = displayDateTime;
    }

    public Header withDisplayDateTime(String displayDateTime) {
        this.displayDateTime = displayDateTime;
        return this;
    }

    /**
     * Date and time after which display of message is no longer needed
     * (Required)
     * 
     * @return
     *     The validityDateTime
     */
    @JsonProperty("ValidityDateTime")
    public String getValidityDateTime() {
        return validityDateTime;
    }

    /**
     * Date and time after which display of message is no longer needed
     * (Required)
     * 
     * @param validityDateTime
     *     The ValidityDateTime
     */
    @JsonProperty("ValidityDateTime")
    public void setValidityDateTime(String validityDateTime) {
        this.validityDateTime = validityDateTime;
    }

    public Header withValidityDateTime(String validityDateTime) {
        this.validityDateTime = validityDateTime;
        return this;
    }

    /**
     *  Time (In seconds) a message is displayed before automatically being dismissed.  Value of 0 would mean subscriber must explicitly dismiss the message with the dismiss button. Autohide time only applies to  plain text messages, not to URL based messages.
     * (Required)
     * 
     * @return
     *     The autoHideTime
     */
    @JsonProperty("AutoHideTime")
    public Integer getAutoHideTime() {
        return autoHideTime;
    }

    /**
     *  Time (In seconds) a message is displayed before automatically being dismissed.  Value of 0 would mean subscriber must explicitly dismiss the message with the dismiss button. Autohide time only applies to  plain text messages, not to URL based messages.
     * (Required)
     * 
     * @param autoHideTime
     *     The AutoHideTime
     */
    @JsonProperty("AutoHideTime")
    public void setAutoHideTime(Integer autoHideTime) {
        this.autoHideTime = autoHideTime;
    }

    public Header withAutoHideTime(Integer autoHideTime) {
        this.autoHideTime = autoHideTime;
        return this;
    }

    /**
     * Status of message. Default value is Inactive.
     * 
     * @return
     *     The status
     */
    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    /**
     * Status of message. Default value is Inactive.
     * 
     * @param status
     *     The Status
     */
    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    public Header withStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageId).append(messageName).append(distributionMode).append(isPopupMessage).append(messageType).append(creationDateTime).append(displayDateTime).append(validityDateTime).append(autoHideTime).append(status).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Header) == false) {
            return false;
        }
        Header rhs = ((Header) other);
        return new EqualsBuilder().append(messageId, rhs.messageId).append(messageName, rhs.messageName).append(distributionMode, rhs.distributionMode).append(isPopupMessage, rhs.isPopupMessage).append(messageType, rhs.messageType).append(creationDateTime, rhs.creationDateTime).append(displayDateTime, rhs.displayDateTime).append(validityDateTime, rhs.validityDateTime).append(autoHideTime, rhs.autoHideTime).append(status, rhs.status).isEquals();
    }

    @Generated("org.jsonschema2pojo")
    public enum DistributionMode {

        MULTICAST("Multicast"),
        BROADCAST("Broadcast"),
        UNICAST("Unicast");
        private final String value;
        private final static Map<String, Header.DistributionMode> CONSTANTS = new HashMap<String, Header.DistributionMode>();

        static {
            for (Header.DistributionMode c: values()) {
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
        public static Header.DistributionMode fromValue(String value) {
            Header.DistributionMode constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("org.jsonschema2pojo")
    public enum MessageType {

        PRODUCT("Product"),
        SERVICE("Service"),
        CTA("CTA");
        private final String value;
        private final static Map<String, Header.MessageType> CONSTANTS = new HashMap<String, Header.MessageType>();

        static {
            for (Header.MessageType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private MessageType(String value) {
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
        public static Header.MessageType fromValue(String value) {
            Header.MessageType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
