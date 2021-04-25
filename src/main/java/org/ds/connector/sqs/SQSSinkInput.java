package org.ds.connector.sqs;

import com.amazonaws.services.sqs.model.Message;

public class SQSSinkInput {
    private String messageBody;
    private Message upstreamMessageContext;

    public SQSSinkInput(String messageBody, Message upstreamMessageContext) {
        this.messageBody = messageBody;
        this.upstreamMessageContext = upstreamMessageContext;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Message getUpstreamMessageContext() {
        return upstreamMessageContext;
    }

    public void setUpstreamMessageContext(Message upstreamMessageContext) {
        this.upstreamMessageContext = upstreamMessageContext;
    }

    @Override
    public String toString() {
        return "SQSSinkInput{" +
                "messageBody='" + messageBody + '\'' +
                ", upstreamMessageContext=" + upstreamMessageContext +
                '}';
    }
}
