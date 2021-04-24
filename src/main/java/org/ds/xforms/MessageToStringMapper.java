package org.ds.xforms;

import com.amazonaws.services.sqs.model.Message;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.HashMap;

public class MessageToStringMapper implements MapFunction<Tuple2<Message, HashMap>, String> {
    @Override
    public String map(Tuple2<Message, HashMap> messageHashMapTuple2) throws Exception {
        return messageHashMapTuple2.f0.getBody();
    }
}
