
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
    "UnicastMessageAssociationsList"
})
public class UnicastMessageAssociationResponse implements Serializable
{

    @JsonProperty("UnicastMessageAssociationsList")
    private List<UnicastMessageAssociation> unicastMessageAssociationsList = new ArrayList<UnicastMessageAssociation>();
    private final static long serialVersionUID = 5683557324221561870L;

    /**
     * 
     * @return
     *     The unicastMessageAssociationsList
     */
    @JsonProperty("UnicastMessageAssociationsList")
    public List<UnicastMessageAssociation> getUnicastMessageAssociationsList() {
        return unicastMessageAssociationsList;
    }

    /**
     * 
     * @param unicastMessageAssociationsList
     *     The UnicastMessageAssociationsList
     */
    @JsonProperty("UnicastMessageAssociationsList")
    public void setUnicastMessageAssociationsList(List<UnicastMessageAssociation> unicastMessageAssociationsList) {
        this.unicastMessageAssociationsList = unicastMessageAssociationsList;
    }

    public UnicastMessageAssociationResponse withUnicastMessageAssociationsList(List<UnicastMessageAssociation> unicastMessageAssociationsList) {
        this.unicastMessageAssociationsList = unicastMessageAssociationsList;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(unicastMessageAssociationsList).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UnicastMessageAssociationResponse) == false) {
            return false;
        }
        UnicastMessageAssociationResponse rhs = ((UnicastMessageAssociationResponse) other);
        return new EqualsBuilder().append(unicastMessageAssociationsList, rhs.unicastMessageAssociationsList).isEquals();
    }

}
