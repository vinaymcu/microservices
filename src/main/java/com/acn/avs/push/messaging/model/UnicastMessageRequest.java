
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
    "UnicastMessage"
})
public class UnicastMessageRequest implements Serializable
{

    @JsonProperty("UnicastMessage")
    private List<UnicastMessage> unicastMessage = new ArrayList<UnicastMessage>();
    private final static long serialVersionUID = -7859906316880935759L;

    /**
     * 
     * @return
     *     The unicastMessage
     */
    @JsonProperty("UnicastMessage")
    public List<UnicastMessage> getUnicastMessage() {
        return unicastMessage;
    }

    /**
     * 
     * @param unicastMessage
     *     The UnicastMessage
     */
    @JsonProperty("UnicastMessage")
    public void setUnicastMessage(List<UnicastMessage> unicastMessage) {
        this.unicastMessage = unicastMessage;
    }

    public UnicastMessageRequest withUnicastMessage(List<UnicastMessage> unicastMessage) {
        this.unicastMessage = unicastMessage;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(unicastMessage).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UnicastMessageRequest) == false) {
            return false;
        }
        UnicastMessageRequest rhs = ((UnicastMessageRequest) other);
        return new EqualsBuilder().append(unicastMessage, rhs.unicastMessage).isEquals();
    }

}
