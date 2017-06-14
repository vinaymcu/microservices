
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
    "MulticastMessages"
})
public class MulticastMessageRequest implements Serializable
{

    @JsonProperty("MulticastMessages")
    private List<MulticastMessage> multicastMessages = new ArrayList<MulticastMessage>();
    private final static long serialVersionUID = 7110599700244427372L;

    /**
     * 
     * @return
     *     The multicastMessages
     */
    @JsonProperty("MulticastMessages")
    public List<MulticastMessage> getMulticastMessages() {
        return multicastMessages;
    }

    /**
     * 
     * @param multicastMessages
     *     The MulticastMessages
     */
    @JsonProperty("MulticastMessages")
    public void setMulticastMessages(List<MulticastMessage> multicastMessages) {
        this.multicastMessages = multicastMessages;
    }

    public MulticastMessageRequest withMulticastMessages(List<MulticastMessage> multicastMessages) {
        this.multicastMessages = multicastMessages;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(multicastMessages).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MulticastMessageRequest) == false) {
            return false;
        }
        MulticastMessageRequest rhs = ((MulticastMessageRequest) other);
        return new EqualsBuilder().append(multicastMessages, rhs.multicastMessages).isEquals();
    }

}
