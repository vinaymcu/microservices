
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
    "Message"
})
public class Messages implements Serializable
{

    @JsonProperty("Message")
    private List<Message> message = new ArrayList<Message>();
    private final static long serialVersionUID = 3163739366451995759L;

    /**
     * 
     * @return
     *     The message
     */
    @JsonProperty("Message")
    public List<Message> getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The Message
     */
    @JsonProperty("Message")
    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public Messages withMessage(List<Message> message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(message).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Messages) == false) {
            return false;
        }
        Messages rhs = ((Messages) other);
        return new EqualsBuilder().append(message, rhs.message).isEquals();
    }

}
