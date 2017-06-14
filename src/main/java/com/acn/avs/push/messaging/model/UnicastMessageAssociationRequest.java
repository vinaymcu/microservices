
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
    "UnicastMessageAssociations"
})
public class UnicastMessageAssociationRequest implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("UnicastMessageAssociations")
    private UnicastMessageAssociation unicastMessageAssociations;
    private final static long serialVersionUID = 7476937714800917216L;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The unicastMessageAssociations
     */
    @JsonProperty("UnicastMessageAssociations")
    public UnicastMessageAssociation getUnicastMessageAssociations() {
        return unicastMessageAssociations;
    }

    /**
     * 
     * (Required)
     * 
     * @param unicastMessageAssociations
     *     The UnicastMessageAssociations
     */
    @JsonProperty("UnicastMessageAssociations")
    public void setUnicastMessageAssociations(UnicastMessageAssociation unicastMessageAssociations) {
        this.unicastMessageAssociations = unicastMessageAssociations;
    }

    public UnicastMessageAssociationRequest withUnicastMessageAssociations(UnicastMessageAssociation unicastMessageAssociations) {
        this.unicastMessageAssociations = unicastMessageAssociations;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(unicastMessageAssociations).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UnicastMessageAssociationRequest) == false) {
            return false;
        }
        UnicastMessageAssociationRequest rhs = ((UnicastMessageAssociationRequest) other);
        return new EqualsBuilder().append(unicastMessageAssociations, rhs.unicastMessageAssociations).isEquals();
    }

}
