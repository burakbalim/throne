package throne.kafka.common;

import org.apache.kafka.common.serialization.Serializer;
import throne.orchestration.common.IData;

import java.util.Map;

public class IMessageSerializer implements Serializer<IData> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, IData data) {
        return data.getMessage().getBytes();
    }

    @Override
    public void close() {

    }
}
