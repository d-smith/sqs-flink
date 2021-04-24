package org.ds.xforms;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import com.amazonaws.services.sqs.model.Message;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.HashMap;

public class MessageToFilterableMapper implements MapFunction<Message, Tuple2<Message, HashMap>> {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public Tuple2<Message, HashMap> map(Message message) throws Exception {
        HashMap messageAttributes = new ObjectMapper().readValue(message.getBody(), HashMap.class);
        return new Tuple2<>(message, messageAttributes);
    }
}
