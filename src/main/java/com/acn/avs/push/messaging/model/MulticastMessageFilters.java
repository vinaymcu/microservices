
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
    "MulticastMessageFilter"
})
public class MulticastMessageFilters implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("MulticastMessageFilter")
    private MulticastMessageFilter multicastMessageFilter;
    private final static long serialVersionUID = -8413453710502818666L;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The multicastMessageFilter
     */
    @JsonProperty("MulticastMessageFilter")
    public MulticastMessageFilter getMulticastMessageFilter() {
        return multicastMessageFilter;
    }

    /**
     * 
     * (Required)
     * 
     * @param multicastMessageFilter
     *     The MulticastMessageFilter
     */
    @JsonProperty("MulticastMessageFilter")
    public void setMulticastMessageFilter(MulticastMessageFilter multicastMessageFilter) {
        this.multicastMessageFilter = multicastMessageFilter;
    }

    public MulticastMessageFilters withMulticastMessageFilter(MulticastMessageFilter multicastMessageFilter) {
        this.multicastMessageFilter = multicastMessageFilter;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(multicastMessageFilter).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof MulticastMessageFilters) == false) {
            return false;
        }
        MulticastMessageFilters rhs = ((MulticastMessageFilters) other);
        return new EqualsBuilder().append(multicastMessageFilter, rhs.multicastMessageFilter).isEquals();
    }

}
