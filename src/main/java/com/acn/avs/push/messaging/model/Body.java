
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
    "MessageURL",
    "MessageText",
    "MessagePopupText"
})
public class Body implements Serializable
{

    /**
     * URL are message to be display on client device
     * 
     */
    @JsonProperty("MessageURL")
    @JsonPropertyDescription("")
    private String messageURL;
    /**
     * Message text to be display on client device
     * 
     */
    @JsonProperty("MessageText")
    @JsonPropertyDescription("")
    private String messageText;
    /**
     * Message text if Popup message is set as true
     * 
     */
    @JsonProperty("MessagePopupText")
    @JsonPropertyDescription("")
    private String messagePopupText;
    private final static long serialVersionUID = -4917024608276880791L;

    /**
     * URL are message to be display on client device
     * 
     * @return
     *     The messageURL
     */
    @JsonProperty("MessageURL")
    public String getMessageURL() {
        return messageURL;
    }

    /**
     * URL are message to be display on client device
     * 
     * @param messageURL
     *     The MessageURL
     */
    @JsonProperty("MessageURL")
    public void setMessageURL(String messageURL) {
        this.messageURL = messageURL;
    }

    public Body withMessageURL(String messageURL) {
        this.messageURL = messageURL;
        return this;
    }

    /**
     * Message text to be display on client device
     * 
     * @return
     *     The messageText
     */
    @JsonProperty("MessageText")
    public String getMessageText() {
        return messageText;
    }

    /**
     * Message text to be display on client device
     * 
     * @param messageText
     *     The MessageText
     */
    @JsonProperty("MessageText")
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Body withMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    /**
     * Message text if Popup message is set as true
     * 
     * @return
     *     The messagePopupText
     */
    @JsonProperty("MessagePopupText")
    public String getMessagePopupText() {
        return messagePopupText;
    }

    /**
     * Message text if Popup message is set as true
     * 
     * @param messagePopupText
     *     The MessagePopupText
     */
    @JsonProperty("MessagePopupText")
    public void setMessagePopupText(String messagePopupText) {
        this.messagePopupText = messagePopupText;
    }

    public Body withMessagePopupText(String messagePopupText) {
        this.messagePopupText = messagePopupText;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageURL).append(messageText).append(messagePopupText).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Body) == false) {
            return false;
        }
        Body rhs = ((Body) other);
        return new EqualsBuilder().append(messageURL, rhs.messageURL).append(messageText, rhs.messageText).append(messagePopupText, rhs.messagePopupText).isEquals();
    }

}
