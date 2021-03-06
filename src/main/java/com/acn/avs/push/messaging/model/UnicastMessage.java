
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
    "Header",
    "Body",
    "SubscriberId",
    "Stbs"
})
public class UnicastMessage implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Header")
    private Header header;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Body")
    private Body body;
    /**
     * Id of Subscriber
     * 
     */
    @JsonProperty("SubscriberId")
    @JsonPropertyDescription("")
    private String subscriberId;
    /**
     * 
     */
    @JsonProperty("Stbs")
    private Stbs stbs;
    private final static long serialVersionUID = -1352177758068957219L;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The header
     */
    @JsonProperty("Header")
    public Header getHeader() {
        return header;
    }

    /**
     * 
     * (Required)
     * 
     * @param header
     *     The Header
     */
    @JsonProperty("Header")
    public void setHeader(Header header) {
        this.header = header;
    }

    public UnicastMessage withHeader(Header header) {
        this.header = header;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The body
     */
    @JsonProperty("Body")
    public Body getBody() {
        return body;
    }

    /**
     * 
     * (Required)
     * 
     * @param body
     *     The Body
     */
    @JsonProperty("Body")
    public void setBody(Body body) {
        this.body = body;
    }

    public UnicastMessage withBody(Body body) {
        this.body = body;
        return this;
    }

    /**
     * Id of Subscriber
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
     * 
     * @param subscriberId
     *     The SubscriberId
     */
    @JsonProperty("SubscriberId")
    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public UnicastMessage withSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    /**
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
     * @param stbs
     *     The Stbs
     */
    @JsonProperty("Stbs")
    public void setStbs(Stbs stbs) {
        this.stbs = stbs;
    }

    public UnicastMessage withStbs(Stbs stbs) {
        this.stbs = stbs;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(header).append(body).append(subscriberId).append(stbs).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UnicastMessage) == false) {
            return false;
        }
        UnicastMessage rhs = ((UnicastMessage) other);
        return new EqualsBuilder().append(header, rhs.header).append(body, rhs.body).append(subscriberId, rhs.subscriberId).append(stbs, rhs.stbs).isEquals();
    }

}
