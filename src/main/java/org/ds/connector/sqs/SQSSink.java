package org.ds.connector.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

public class SQSSink extends RichSinkFunction<String> {
    private SQSConnectorConfig sqsConnectorConfig;
    private AmazonSQS client;

    public SQSSink(SQSConnectorConfig sqsConnectorConfig) {
        this.sqsConnectorConfig = sqsConnectorConfig;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        //super.open(parameters);
        client = AmazonSQSClientBuilder.standard().withRegion(sqsConnectorConfig.getRegion()).build();
    }

    @Override
    public void close() throws Exception {
        //super.close();
    }

    @Override
    public void invoke(String value, Context context) throws Exception {
        client.sendMessage(sqsConnectorConfig.getQueueUrl(), value);
    }
}
