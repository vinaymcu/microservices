
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
    "Stb"
})
public class Stbs implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("Stb")
    private List<Stb> stb = new ArrayList<Stb>();
    private final static long serialVersionUID = -8679549685779191171L;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The stb
     */
    @JsonProperty("Stb")
    public List<Stb> getStb() {
        return stb;
    }

    /**
     * 
     * (Required)
     * 
     * @param stb
     *     The Stb
     */
    @JsonProperty("Stb")
    public void setStb(List<Stb> stb) {
        this.stb = stb;
    }

    public Stbs withStb(List<Stb> stb) {
        this.stb = stb;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(stb).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Stbs) == false) {
            return false;
        }
        Stbs rhs = ((Stbs) other);
        return new EqualsBuilder().append(stb, rhs.stb).isEquals();
    }

}
