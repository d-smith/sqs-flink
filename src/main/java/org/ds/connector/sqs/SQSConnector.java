package org.ds.connector.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

import java.util.List;

public class SQSConnector extends RichParallelSourceFunction<Message> {

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
            }
        }
    }

    @Override
    public void cancel() {
        this.running = false;
    }
}
