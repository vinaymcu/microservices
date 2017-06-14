
package com.acn.avs.push.messaging.model;

import java.io.Serializable;
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
    "Messages"
})
public class GetMessagesResponse implements Serializable
{

    @JsonProperty("Messages")
    private Messages messages;
    private final static long serialVersionUID = -8038571446107925714L;

    /**
     * 
     * @return
     *     The messages
     */
    @JsonProperty("Messages")
    public Messages getMessages() {
        return messages;
    }

    /**
     * 
     * @param messages
     *     The Messages
     */
    @JsonProperty("Messages")
    public void setMessages(Messages messages) {
        this.messages = messages;
    }

    public GetMessagesResponse withMessages(Messages messages) {
        this.messages = messages;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messages).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetMessagesResponse) == false) {
            return false;
        }
        GetMessagesResponse rhs = ((GetMessagesResponse) other);
        return new EqualsBuilder().append(messages, rhs.messages).isEquals();
    }

}
