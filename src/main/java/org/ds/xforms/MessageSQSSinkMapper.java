package org.ds.xforms;

import com.amazonaws.services.sqs.model.Message;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.ds.connector.sqs.SQSSinkInput;

import java.util.HashMap;

public class MessageSQSSinkMapper implements MapFunction<Tuple2<Message, HashMap>, SQSSinkInput> {
    @Override
    public SQSSinkInput map(Tuple2<Message, HashMap> messageHashMapTuple2) throws Exception {
        return new SQSSinkInput(messageHashMapTuple2.f0.getBody(), messageHashMapTuple2.f0) ;
    }
}
