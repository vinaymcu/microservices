
package com.acn.avs.push.messaging.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "MessageType"
})
public class MessageTypesResponse implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("MessageType")
    private List<String> messageType = new ArrayList<String>();
    private final static long serialVersionUID = 5457063875182305432L;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The messageType
     */
    @JsonProperty("MessageType")
    public List<String> getMessageType() {
        return messageType;
    }

    /**
     * 
     * (Required)
     * 
     * @param messageType
     *     The MessageType
     */
    @JsonProperty("MessageType")
    public void setMessageType(List<String> messageType) {
        this.messageType = messageType;
    }

    public MessageTypesResponse withMessageType(List<String> messageType) {
        this.messageType = messageType;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageType).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MessageTypesResponse) == false) {
            return false;
        }
        MessageTypesResponse rhs = ((MessageTypesResponse) other);
        return new EqualsBuilder().append(messageType, rhs.messageType).isEquals();
    }

}
