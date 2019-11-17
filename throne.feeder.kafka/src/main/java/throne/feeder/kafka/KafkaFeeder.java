package throne.feeder.kafka;

import org.apache.kafka.clients.producer.*;
import throne.kafka.common.IMessageSerializer;
import throne.orchestration.common.FeederBase;
import throne.orchestration.common.FeederType;
import throne.orchestration.common.IData;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.exception.OrchestrationException;
import throne.orchestration.common.util.OrchestrationUtil;

import java.util.Properties;

public class KafkaFeeder extends FeederBase {

    private KafkaConfiguration kafkaConfiguration;
    private KafkaProducer<String, IData> kafkaProducer;

    @Override
    public void open() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getServerConfig());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaConfiguration.getClientIdConfig());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IMessageSerializer.class.getName());
        kafkaProducer = new KafkaProducer<>(props);
        this.onOpen();
    }

    @Override
    public void close() {
        this.onClose();
    }

    @Override
    public FeederType getType() {
        return FeederType.Kafka;
    }

    @Override
    protected void produce(String topic, IData data) {
        kafkaProducer.send(new ProducerRecord<>(topic, data));
    }

    @Override
    public String name() {
        return kafkaConfiguration.getName();
    }

    @Override
    public void configure(String path) throws FeederException {
        try {
            kafkaConfiguration = OrchestrationUtil.readJson(OrchestrationUtil.readFile(path), KafkaConfiguration.class);
        } catch (OrchestrationException e) {
            throw new FeederException(name() + " configuration exception", e);
        }
    }
}
