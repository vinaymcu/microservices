
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
    "Filter"
})
public class MulticastMessageFilter implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Filter")
    private List<Filter> filter = new ArrayList<Filter>();
    private final static long serialVersionUID = 954747668542783108L;

    /**
     * 
     * (Required)
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
     * (Required)
     * 
     * @param filter
     *     The Filter
     */
    @JsonProperty("Filter")
    public void setFilter(List<Filter> filter) {
        this.filter = filter;
    }

    public MulticastMessageFilter withFilter(List<Filter> filter) {
        this.filter = filter;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(filter).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MulticastMessageFilter) == false) {
            return false;
        }
        MulticastMessageFilter rhs = ((MulticastMessageFilter) other);
        return new EqualsBuilder().append(filter, rhs.filter).isEquals();
    }

}
