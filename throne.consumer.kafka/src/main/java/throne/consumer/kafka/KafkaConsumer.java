package throne.consumer.kafka;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import throne.kafka.common.IMessageDeserializer;
import throne.orchestration.common.ConsumerBase;
import throne.orchestration.common.IData;
import throne.orchestration.common.exception.ConsumerException;
import throne.orchestration.common.exception.OrchestractionException;
import throne.orchestration.common.util.ConfigurationUtil;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaConsumer extends ConsumerBase {

    private KafkaConsumerConfiguration consumerConfiguration;
    private Consumer<Long, String> consumer;

    @Override
    public void start() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerConfiguration.getServerConfig());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerConfiguration.getOffsetResetEarlier());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IMessageDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IMessageDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerConfiguration.getPoolRecord());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerConfiguration.getOffsetResetEarlier());
        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(consumerConfiguration.getTopic()));
        this.onOpen();
    }

    @Override
    public void stop() {
        consumer.close();
        this.onStop();
    }

    @Override
    protected List<IData> onConsume() throws ConsumerException {
        List<IData> messages = new ArrayList<>();
        ConsumerRecords<Long, String> poll = consumer.poll(Duration.from(ChronoUnit.HOURS.getDuration()));
        Iterable<ConsumerRecord<Long, String>> records = poll.records(consumerConfiguration.getTopic());

        for (ConsumerRecord<Long, String> i : records) {
            IData message = (IData) new Gson().fromJson(i.value(), getClazz());
            messages.add(message);
        }

        return messages;
    }

    private Class getClazz() throws ConsumerException {
        String dataClazz = consumerConfiguration.getDataClazz();
        try {
            return Class.forName(dataClazz);
        } catch (ClassNotFoundException e) {
            throw new ConsumerException("", e, consumerConfiguration.getName());
        }
    }

    @Override
    public void configure(String path) throws ConsumerException {
        String configuration = null;
        try {
            configuration = ConfigurationUtil.readFile(path);
        } catch (OrchestractionException e) {
            throw new ConsumerException("File read exception while consumer ", e, consumerConfiguration.getName());
        }
        consumerConfiguration = new Gson().fromJson(configuration, KafkaConsumerConfiguration.class);
    }
}
