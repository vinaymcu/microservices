
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
    "Header",
    "Body",
    "Filter"
})
public class MulticastMessage implements Serializable
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
    @JsonProperty("Filter")
    private List<Filter> filter = new ArrayList<Filter>();
    private final static long serialVersionUID = -6093963420395587437L;

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

    public MulticastMessage withHeader(Header header) {
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

    public MulticastMessage withBody(Body body) {
        this.body = body;
        return this;
    }

    /**
     * 
     * @return
     *     The filter
     */
    @JsonProperty("Filter")
    public List<Filter> getFilter() {
        return filter;
    }

    /**
     * 
     * @param filter
     *     The Filter
     */
    @JsonProperty("Filter")
    public void setFilter(List<Filter> filter) {
        this.filter = filter;
    }

    public MulticastMessage withFilter(List<Filter> filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(header).append(body).append(filter).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MulticastMessage) == false) {
            return false;
        }
        MulticastMessage rhs = ((MulticastMessage) other);
        return new EqualsBuilder().append(header, rhs.header).append(body, rhs.body).append(filter, rhs.filter).isEquals();
    }

}
