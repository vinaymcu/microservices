
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
    "MAC_Address"
})
public class Stb implements Serializable
{

    /**
     * MAC Address of STB
     * (Required)
     * 
     */
    @JsonProperty("MAC_Address")
    @JsonPropertyDescription("")
    private String mACAddress;
    private final static long serialVersionUID = 5333420778978296384L;

    /**
     * MAC Address of STB
     * (Required)
     * 
     * @return
     *     The mACAddress
     */
    @JsonProperty("MAC_Address")
    public String getMACAddress() {
        return mACAddress;
    }

    /**
     * MAC Address of STB
     * (Required)
     * 
     * @param mACAddress
     *     The MAC_Address
     */
    @JsonProperty("MAC_Address")
    public void setMACAddress(String mACAddress) {
        this.mACAddress = mACAddress;
    }

    public Stb withMACAddress(String mACAddress) {
        this.mACAddress = mACAddress;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mACAddress).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Stb) == false) {
            return false;
        }
        Stb rhs = ((Stb) other);
        return new EqualsBuilder().append(mACAddress, rhs.mACAddress).isEquals();
    }

}
