
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
    "MessageIds"
})
public class MessageIds implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("MessageIds")
    private List<MessageId> messageIds = new ArrayList<MessageId>();
    private final static long serialVersionUID = 7935512099325806201L;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The messageIds
     */
    @JsonProperty("MessageIds")
    public List<MessageId> getMessageIds() {
        return messageIds;
    }

    /**
     * 
     * (Required)
     * 
     * @param messageIds
     *     The MessageIds
     */
    @JsonProperty("MessageIds")
    public void setMessageIds(List<MessageId> messageIds) {
        this.messageIds = messageIds;
    }

    public MessageIds withMessageIds(List<MessageId> messageIds) {
        this.messageIds = messageIds;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageIds).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MessageIds) == false) {
            return false;
        }
        MessageIds rhs = ((MessageIds) other);
        return new EqualsBuilder().append(messageIds, rhs.messageIds).isEquals();
    }

}
