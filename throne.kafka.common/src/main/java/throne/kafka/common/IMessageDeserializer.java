package throne.kafka.common;

import org.apache.kafka.common.serialization.Deserializer;
import throne.orchestration.common.util.ThroneUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

public class IMessageDeserializer implements Deserializer<String> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public String deserialize(String topic, byte[] data) {
        try {
            return ThroneUtil.read(new ByteArrayInputStream(data));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public void close() {

    }
}
