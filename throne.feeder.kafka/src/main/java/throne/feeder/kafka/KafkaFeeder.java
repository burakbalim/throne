package throne.feeder.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import throne.kafka.common.IMessageSerializer;
import throne.orchestration.common.FeederBase;
import throne.orchestration.common.IData;
import throne.orchestration.common.ILogger;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.exception.OrchestrationException;
import throne.orchestration.common.util.OrchestrationUtil;

import java.util.Properties;

public class KafkaFeeder extends FeederBase {

    private ILogger iLogger;
    private KafkaConfiguration kafkaConfiguration;
    private KafkaProducer<String, IData> kafkaProducer;
    private String topic;

    @Override
    public void open() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getServerConfig());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaConfiguration.getClientIdConfig());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, IMessageSerializer.class.getName());
        this.topic = kafkaConfiguration.getTopic();
        kafkaProducer = new KafkaProducer<>(props);
        this.onOpen();
    }

    @Override
    public void close() {
        this.onClose();
    }

    @Override
    protected void produce(IData data) {
        kafkaProducer.send(new ProducerRecord<>(topic, data));
    }

    @Override
    public void configure(String path) throws FeederException {
        try {
            kafkaConfiguration = OrchestrationUtil.readJson(OrchestrationUtil.readFile(path), KafkaConfiguration.class);
        } catch (OrchestrationException e) {
            throw new FeederException("Configuration Exception", e, kafkaConfiguration.getName());
        }
    }

    @Override
    public void setILogger(ILogger logger) {
        this.iLogger = logger;
    }
}
