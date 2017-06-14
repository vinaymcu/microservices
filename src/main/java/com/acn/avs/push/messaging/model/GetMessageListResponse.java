
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
    "MessageList"
})
public class GetMessageListResponse implements Serializable
{

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("MessageList")
    private List<MessageList> messageList = new ArrayList<MessageList>();
    private final static long serialVersionUID = 7461672776869540147L;

    /**
     * 
     * (Required)
     * 
     * @return
     *     The messageList
     */
    @JsonProperty("MessageList")
    public List<MessageList> getMessageList() {
        return messageList;
    }

    /**
     * 
     * (Required)
     * 
     * @param messageList
     *     The MessageList
     */
    @JsonProperty("MessageList")
    public void setMessageList(List<MessageList> messageList) {
        this.messageList = messageList;
    }

    public GetMessageListResponse withMessageList(List<MessageList> messageList) {
        this.messageList = messageList;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(messageList).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GetMessageListResponse) == false) {
            return false;
        }
        GetMessageListResponse rhs = ((GetMessageListResponse) other);
        return new EqualsBuilder().append(messageList, rhs.messageList).isEquals();
    }

}
