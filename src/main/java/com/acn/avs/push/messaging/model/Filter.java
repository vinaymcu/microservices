
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
    "IP_Address",
    "PackageID",
    "Watched_Channel_ID",
    "LocationID",
    "SW_Version",
    "HW_Version",
    "UI_Version",
    "UI_Language",
    "FIPS"
})
public class Filter implements Serializable
{

    /**
     * IP address of Client device
     * 
     */
    @JsonProperty("IP_Address")
    @JsonPropertyDescription("")
    private String iPAddress;
    /**
     * Id of Package assigned to the subscriber
     * 
     */
    @JsonProperty("PackageID")
    @JsonPropertyDescription("")
    private String packageID;
    /**
     * External ID of the watched channel
     * 
     */
    @JsonProperty("Watched_Channel_ID")
    @JsonPropertyDescription("")
    private String watchedChannelID;
    /**
     * Location Id of subscriber
     * 
     */
    @JsonProperty("LocationID")
    @JsonPropertyDescription("")
    private String locationID;
    /**
     * SW version currently on the client device
     * 
     */
    @JsonProperty("SW_Version")
    @JsonPropertyDescription("")
    private String sWVersion;
    /**
     * HW version currently on the client device
     * 
     */
    @JsonProperty("HW_Version")
    @JsonPropertyDescription("")
    private String hWVersion;
    /**
     * UI version currently on the client device
     * 
     */
    @JsonProperty("UI_Version")
    @JsonPropertyDescription("")
    private String uIVersion;
    /**
     * UI language currently on the client device
     * 
     */
    @JsonProperty("UI_Language")
    @JsonPropertyDescription("")
    private String uILanguage;
    /**
     * FIPS Code
     * 
     */
    @JsonProperty("FIPS")
    @JsonPropertyDescription("")
    private String fIPS;
    private final static long serialVersionUID = 21246897189726509L;

    /**
     * IP address of Client device
     * 
     * @return
     *     The iPAddress
     */
    @JsonProperty("IP_Address")
    public String getIPAddress() {
        return iPAddress;
    }

    /**
     * IP address of Client device
     * 
     * @param iPAddress
     *     The IP_Address
     */
    @JsonProperty("IP_Address")
    public void setIPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
    }

    public Filter withIPAddress(String iPAddress) {
        this.iPAddress = iPAddress;
        return this;
    }

    /**
     * Id of Package assigned to the subscriber
     * 
     * @return
     *     The packageID
     */
    @JsonProperty("PackageID")
    public String getPackageID() {
        return packageID;
    }

    /**
     * Id of Package assigned to the subscriber
     * 
     * @param packageID
     *     The PackageID
     */
    @JsonProperty("PackageID")
    public void setPackageID(String packageID) {
        this.packageID = packageID;
    }

    public Filter withPackageID(String packageID) {
        this.packageID = packageID;
        return this;
    }

    /**
     * External ID of the watched channel
     * 
     * @return
     *     The watchedChannelID
     */
    @JsonProperty("Watched_Channel_ID")
    public String getWatchedChannelID() {
        return watchedChannelID;
    }

    /**
     * External ID of the watched channel
     * 
     * @param watchedChannelID
     *     The Watched_Channel_ID
     */
    @JsonProperty("Watched_Channel_ID")
    public void setWatchedChannelID(String watchedChannelID) {
        this.watchedChannelID = watchedChannelID;
    }

    public Filter withWatchedChannelID(String watchedChannelID) {
        this.watchedChannelID = watchedChannelID;
        return this;
    }

    /**
     * Location Id of subscriber
     * 
     * @return
     *     The locationID
     */
    @JsonProperty("LocationID")
    public String getLocationID() {
        return locationID;
    }

    /**
     * Location Id of subscriber
     * 
     * @param locationID
     *     The LocationID
     */
    @JsonProperty("LocationID")
    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public Filter withLocationID(String locationID) {
        this.locationID = locationID;
        return this;
    }

    /**
     * SW version currently on the client device
     * 
     * @return
     *     The sWVersion
     */
    @JsonProperty("SW_Version")
    public String getSWVersion() {
        return sWVersion;
    }

    /**
     * SW version currently on the client device
     * 
     * @param sWVersion
     *     The SW_Version
     */
    @JsonProperty("SW_Version")
    public void setSWVersion(String sWVersion) {
        this.sWVersion = sWVersion;
    }

    public Filter withSWVersion(String sWVersion) {
        this.sWVersion = sWVersion;
        return this;
    }

    /**
     * HW version currently on the client device
     * 
     * @return
     *     The hWVersion
     */
    @JsonProperty("HW_Version")
    public String getHWVersion() {
        return hWVersion;
    }

    /**
     * HW version currently on the client device
     * 
     * @param hWVersion
     *     The HW_Version
     */
    @JsonProperty("HW_Version")
    public void setHWVersion(String hWVersion) {
        this.hWVersion = hWVersion;
    }

    public Filter withHWVersion(String hWVersion) {
        this.hWVersion = hWVersion;
        return this;
    }

    /**
     * UI version currently on the client device
     * 
     * @return
     *     The uIVersion
     */
    @JsonProperty("UI_Version")
    public String getUIVersion() {
        return uIVersion;
    }

    /**
     * UI version currently on the client device
     * 
     * @param uIVersion
     *     The UI_Version
     */
    @JsonProperty("UI_Version")
    public void setUIVersion(String uIVersion) {
        this.uIVersion = uIVersion;
    }

    public Filter withUIVersion(String uIVersion) {
        this.uIVersion = uIVersion;
        return this;
    }

    /**
     * UI language currently on the client device
     * 
     * @return
     *     The uILanguage
     */
    @JsonProperty("UI_Language")
    public String getUILanguage() {
        return uILanguage;
    }

    /**
     * UI language currently on the client device
     * 
     * @param uILanguage
     *     The UI_Language
     */
    @JsonProperty("UI_Language")
    public void setUILanguage(String uILanguage) {
        this.uILanguage = uILanguage;
    }

    public Filter withUILanguage(String uILanguage) {
        this.uILanguage = uILanguage;
        return this;
    }

    /**
     * FIPS Code
     * 
     * @return
     *     The fIPS
     */
    @JsonProperty("FIPS")
    public String getFIPS() {
        return fIPS;
    }

    /**
     * FIPS Code
     * 
     * @param fIPS
     *     The FIPS
     */
    @JsonProperty("FIPS")
    public void setFIPS(String fIPS) {
        this.fIPS = fIPS;
    }

    public Filter withFIPS(String fIPS) {
        this.fIPS = fIPS;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(iPAddress).append(packageID).append(watchedChannelID).append(locationID).append(sWVersion).append(hWVersion).append(uIVersion).append(uILanguage).append(fIPS).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Filter) == false) {
            return false;
        }
        Filter rhs = ((Filter) other);
        return new EqualsBuilder().append(iPAddress, rhs.iPAddress).append(packageID, rhs.packageID).append(watchedChannelID, rhs.watchedChannelID).append(locationID, rhs.locationID).append(sWVersion, rhs.sWVersion).append(hWVersion, rhs.hWVersion).append(uIVersion, rhs.uIVersion).append(uILanguage, rhs.uILanguage).append(fIPS, rhs.fIPS).isEquals();
    }

}
