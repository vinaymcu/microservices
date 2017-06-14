
package com.acn.avs.push.messaging.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    "resultCode",
    "resultDescription",
    "resultObject",
    "systemTime"
})
public class UnicastMessageResponse implements Serializable
{

    /**
     * It will be valorized only in case of generic Error, that is only in case the MS will not able to execute the requested interface, for example in case of the DataBase is down(ACN_300).
     * 
     */
    @JsonProperty("resultCode")
    @JsonPropertyDescription("")
    private Object resultCode;
    /**
     * Description Error. Example:  300-GENERIC ERROR.  In the other case it will be not present.
     * 
     */
    @JsonProperty("resultDescription")
    @JsonPropertyDescription("")
    private Object resultDescription;
    @JsonProperty("resultObject")
    private List<ResultObject> resultObject = new ArrayList<ResultObject>();
    /**
     * GM Time in Milliseconds
     * 
     */
    @JsonProperty("systemTime")
    @JsonPropertyDescription("")
    private Object systemTime;
    private final static long serialVersionUID = 407424414357750945L;

    /**
     * It will be valorized only in case of generic Error, that is only in case the MS will not able to execute the requested interface, for example in case of the DataBase is down(ACN_300).
     * 
     * @return
     *     The resultCode
     */
    @JsonProperty("resultCode")
    public Object getResultCode() {
        return resultCode;
    }

    /**
     * It will be valorized only in case of generic Error, that is only in case the MS will not able to execute the requested interface, for example in case of the DataBase is down(ACN_300).
     * 
     * @param resultCode
     *     The resultCode
     */
    @JsonProperty("resultCode")
    public void setResultCode(Object resultCode) {
        this.resultCode = resultCode;
    }

    public UnicastMessageResponse withResultCode(Object resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    /**
     * Description Error. Example:  300-GENERIC ERROR.  In the other case it will be not present.
     * 
     * @return
     *     The resultDescription
     */
    @JsonProperty("resultDescription")
    public Object getResultDescription() {
        return resultDescription;
    }

    /**
     * Description Error. Example:  300-GENERIC ERROR.  In the other case it will be not present.
     * 
     * @param resultDescription
     *     The resultDescription
     */
    @JsonProperty("resultDescription")
    public void setResultDescription(Object resultDescription) {
        this.resultDescription = resultDescription;
    }

    public UnicastMessageResponse withResultDescription(Object resultDescription) {
        this.resultDescription = resultDescription;
        return this;
    }

    /**
     * 
     * @return
     *     The resultObject
     */
    @JsonProperty("resultObject")
    public List<ResultObject> getResultObject() {
        return resultObject;
    }

    /**
     * 
     * @param resultObject
     *     The resultObject
     */
    @JsonProperty("resultObject")
    public void setResultObject(List<ResultObject> resultObject) {
        this.resultObject = resultObject;
    }

    public UnicastMessageResponse withResultObject(List<ResultObject> resultObject) {
        this.resultObject = resultObject;
        return this;
    }

    /**
     * GM Time in Milliseconds
     * 
     * @return
     *     The systemTime
     */
    @JsonProperty("systemTime")
    public Object getSystemTime() {
        return systemTime;
    }

    /**
     * GM Time in Milliseconds
     * 
     * @param systemTime
     *     The systemTime
     */
    @JsonProperty("systemTime")
    public void setSystemTime(Object systemTime) {
        this.systemTime = systemTime;
    }

    public UnicastMessageResponse withSystemTime(Object systemTime) {
        this.systemTime = systemTime;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(resultCode).append(resultDescription).append(resultObject).append(systemTime).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof UnicastMessageResponse) == false) {
            return false;
        }
        UnicastMessageResponse rhs = ((UnicastMessageResponse) other);
        return new EqualsBuilder().append(resultCode, rhs.resultCode).append(resultDescription, rhs.resultDescription).append(resultObject, rhs.resultObject).append(systemTime, rhs.systemTime).isEquals();
    }

}
