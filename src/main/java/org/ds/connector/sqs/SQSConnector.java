package org.ds.connector.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SQSConnector extends RichParallelSourceFunction<Message> {

    private static final Logger LOG = LoggerFactory.getLogger(SQSConnector.class);

    private boolean running = true;
    private SQSConnectorConfig sqsConnectorConfig;

    public SQSConnector(SQSConnectorConfig sqsConnectorConfig) {
        this.sqsConnectorConfig = sqsConnectorConfig;
    }


    @Override
    public void run(SourceContext<Message> sourceContext) throws Exception {
        AmazonSQS client = AmazonSQSClientBuilder.standard().withRegion(sqsConnectorConfig.getRegion()).build();
        ReceiveMessageRequest sqsReceiveRequest = new ReceiveMessageRequest()
                .withQueueUrl(sqsConnectorConfig.getQueueUrl())
                .withWaitTimeSeconds(10)
                .withMaxNumberOfMessages(10);

        while(running) {
            List<Message> messages = client.receiveMessage(sqsReceiveRequest).getMessages();
            for(Message m: messages) {
                sourceContext.collect(m);
                LOG.warn("work in progress is removing sqs message from queue - need to pass context downstream for removal post processing... maybe...?");
                DeleteMessageResult deleteMessageResult = client.deleteMessage(
                        new DeleteMessageRequest(
                                sqsConnectorConfig.getQueueUrl(),
                                m.getReceiptHandle()
                        )
                );
                LOG.info(deleteMessageResult.toString());
            }
        }
    }

    @Override
    public void cancel() {
        this.running = false;
    }
}
