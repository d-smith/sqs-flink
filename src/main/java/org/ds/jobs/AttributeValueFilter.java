package org.ds.jobs;

import com.amazonaws.services.sqs.model.Message;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.HashMap;

public class AttributeValueFilter implements FilterFunction<Tuple2<Message, HashMap>> {
    private String attribute;
    private String value;

    public AttributeValueFilter(String attribute, String value) {
        this.attribute = attribute;
        this.value = value;
    }

    @Override
    public boolean filter(Tuple2<Message, HashMap> messageHashMapTuple2) throws Exception {
        HashMap theMap = messageHashMapTuple2.f1;

        if(theMap == null) {
            return false;
        }

        String mapValue = (String) theMap.get(attribute);
        return value.equals(mapValue);
    }
}
