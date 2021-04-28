package org.ds.xforms;

import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.ds.connector.sqs.SQSConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class EventTypeMapper implements MapFunction<Tuple2<Message, HashMap>,Tuple2<Message, HashMap>> {
    private static final Logger LOG = LoggerFactory.getLogger(EventTypeMapper.class);
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Tuple2<Message, HashMap> map(Tuple2<Message, HashMap> inputTuple) throws Exception {
        HashMap theMap = inputTuple.f1;

        if(theMap == null) {
            LOG.warn("no map in input tuple");
            return inputTuple;
        }

        String typeValue = (String) theMap.get("type");
        if("a".equals(typeValue)) {
            LOG.info("transforming type value to new-a");

            //Parse the current body
            HashMap currentBody = mapper.readValue(inputTuple.f0.getBody(), HashMap.class);
            currentBody.put("type", "new-a");
            inputTuple.f0.setBody(mapper.writeValueAsString(currentBody));
            return new Tuple2<>(inputTuple.f0, theMap);
        } else {
            LOG.info("no type to replace");
            return inputTuple;
        }
    }
}
