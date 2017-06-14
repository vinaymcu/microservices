
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
    "MessageId"
})
public class MessageId implements Serializable
{

    /**
     * Message Id of the message.
     * (Required)
     * 
     */
    @JsonProperty("MessageId")
    @JsonPropertyDescription("")
    private Integer messageId;
    private final static long serialVersionUID = -4762072488695164399L;

    /**
     * Message Id of the message.
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
     * Message Id of the message.
     * (Required)
     * 
     * @param messageId
     *     The MessageId
     */
    @JsonProperty("MessageId")
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public MessageId withMessageId(Integer messageId) {
        this.messageId = messageId;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageId).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MessageId) == false) {
            return false;
        }
        MessageId rhs = ((MessageId) other);
        return new EqualsBuilder().append(messageId, rhs.messageId).isEquals();
    }

}
