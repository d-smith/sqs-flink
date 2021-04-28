package org.ds.jobs;

import com.amazonaws.services.sqs.model.Message;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.ds.connector.sqs.SQSConnector;
import org.ds.connector.sqs.SQSConnectorConfig;
import org.ds.connector.sqs.SQSSink;
import org.ds.xforms.AttributeValueFilter;
import org.ds.xforms.EventTypeMapper;
import org.ds.xforms.MessageToFilterableMapper;
import org.ds.xforms.MessageSQSSinkMapper;

public class StreamingJob {
    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SQSConnectorConfig cfg = new SQSConnectorConfig(System.getenv("QUEUE_URL"), System.getenv("AWS_REGION"));

        DataStream<Message> dataStream = env.addSource(new SQSConnector(cfg));
        dataStream
                .map(new MessageToFilterableMapper())
                .map(new EventTypeMapper())
                .filter(new AttributeValueFilter("a","good"))
                .map(new MessageSQSSinkMapper())
                .addSink(new SQSSink(
                        new SQSConnectorConfig(
                                System.getenv("SINK_QUEUE_URL"),
                                System.getenv("AWS_REGION"))));

        env.execute("do it");
    }
}
