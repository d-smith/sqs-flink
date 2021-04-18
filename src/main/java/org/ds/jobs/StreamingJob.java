package org.ds.jobs;

import com.amazonaws.services.sqs.model.Message;
import io.cloudevents.CloudEvent;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.ds.connector.sqs.SQSConnector;
import org.ds.connector.sqs.SQSConnectorConfig;

public class StreamingJob {
    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SQSConnectorConfig cfg = new SQSConnectorConfig(System.getenv("QUEUE_URL"), System.getenv("AWS_REGION"));

        DataStream<Message> dataStream = env.addSource(new SQSConnector(cfg));

        dataStream.print();

        env.execute("do it");
    }
}
