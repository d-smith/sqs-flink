package org.ds.connector.sqs;

import java.io.Serializable;

public class SQSConnectorConfig implements Serializable {
    private String queueUrl;
    private String region;

    public SQSConnectorConfig(String queueUrl, String region) {
        this.queueUrl = queueUrl;
        this.region = region;
    }

    public String getQueueUrl() {
        return queueUrl;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "SQSConnectorConfig{" +
                "queueUrl='" + queueUrl + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
