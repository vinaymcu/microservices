
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
    "EventUpdate"
})
public class EventUpdateRequest implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("EventUpdate")
    private EventUpdate eventUpdate;
    private final static long serialVersionUID = 411839221270325693L;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The eventUpdate
     */
    @JsonProperty("EventUpdate")
    public EventUpdate getEventUpdate() {
        return eventUpdate;
    }

    /**
     * 
     * (Required)
     * 
     * @param eventUpdate
     *     The EventUpdate
     */
    @JsonProperty("EventUpdate")
    public void setEventUpdate(EventUpdate eventUpdate) {
        this.eventUpdate = eventUpdate;
    }

    public EventUpdateRequest withEventUpdate(EventUpdate eventUpdate) {
        this.eventUpdate = eventUpdate;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(eventUpdate).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EventUpdateRequest) == false) {
            return false;
        }
        EventUpdateRequest rhs = ((EventUpdateRequest) other);
        return new EqualsBuilder().append(eventUpdate, rhs.eventUpdate).isEquals();
    }

}
