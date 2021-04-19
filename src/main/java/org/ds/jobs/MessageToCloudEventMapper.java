package org.ds.jobs;

import com.amazonaws.services.sqs.model.Message;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import org.apache.flink.api.common.functions.MapFunction;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.UUID;

public class MessageToCloudEventMapper implements MapFunction<Message, CloudEvent> {
    @Override
    public CloudEvent map(Message message) throws Exception {
        CloudEvent ce = CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("ab/c/d"))
                .withData("text/plain", message.getBody().getBytes())
                .withType("e1")
                .withTime(OffsetDateTime.now())
                .build();
        return ce;
    }
}
