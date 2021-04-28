package org.ds.connector.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.Message;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQSSink extends RichSinkFunction<SQSSinkInput> {
    private SQSConnectorConfig sqsConnectorConfig;
    private AmazonSQS client;
    private static final Logger LOG = LoggerFactory.getLogger(SQSSink.class);

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
    public void invoke(SQSSinkInput value, Context context) throws Exception {
        LOG.info("write {} to {}", value.getMessageBody().toString(), sqsConnectorConfig.getQueueUrl());
        client.sendMessage(sqsConnectorConfig.getQueueUrl(), value.getMessageBody());

        Message upstream = value.getUpstreamMessageContext();
        if(upstream != null) {
            LOG.info("delete upstream sqs message");
            DeleteMessageResult deleteMessageResult = client.deleteMessage(
                    new DeleteMessageRequest(
                            upstream.getAttributes().get("QURL"),
                            upstream.getReceiptHandle()
                    )
            );
        }
    }
}
