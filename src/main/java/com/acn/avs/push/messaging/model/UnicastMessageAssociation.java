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
    "SubscriberId",
    "Stbs"
})
public class UnicastMessageAssociation implements Serializable
{

    /**
     * Id of Subscriber
     * (Required)
     * 
     */
    @JsonProperty("SubscriberId")
    @JsonPropertyDescription("")
    private String subscriberId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Stbs")
    private Stbs stbs;
    private final static long serialVersionUID = -4733452965051131168L;

    /**
     * Id of Subscriber
     * (Required)
     * 
     * @return
     *     The subscriberId
     */
    @JsonProperty("SubscriberId")
    public String getSubscriberId() {
        return subscriberId;
    }

    /**
     * Id of Subscriber
     * (Required)
     * 
     * @param subscriberId
     *     The SubscriberId
     */
    @JsonProperty("SubscriberId")
    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public UnicastMessageAssociation withSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The stbs
     */
    @JsonProperty("Stbs")
    public Stbs getStbs() {
        return stbs;
    }

    /**
     * 
     * (Required)
     * 
     * @param stbs
     *     The Stbs
     */
    @JsonProperty("Stbs")
    public void setStbs(Stbs stbs) {
        this.stbs = stbs;
    }

    public UnicastMessageAssociation withStbs(Stbs stbs) {
        this.stbs = stbs;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(subscriberId).append(stbs).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UnicastMessageAssociation) == false) {
            return false;
        }
        UnicastMessageAssociation rhs = ((UnicastMessageAssociation) other);
        return new EqualsBuilder().append(subscriberId, rhs.subscriberId).append(stbs, rhs.stbs).isEquals();
    }

}
