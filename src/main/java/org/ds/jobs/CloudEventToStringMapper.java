package org.ds.jobs;

import io.cloudevents.CloudEvent;
import org.apache.flink.api.common.functions.MapFunction;

public class CloudEventToStringMapper implements MapFunction<CloudEvent, String> {
    @Override
    public String map(CloudEvent cloudEvent) throws Exception {
        if(cloudEvent != null)
            return cloudEvent.toString();
        else
            return "huh?";
    }
}
