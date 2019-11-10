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
import throne.orchestration.common.exception.OrchestrationException;
import throne.orchestration.common.util.OrchestrationUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class KafkaConsumer extends ConsumerBase {

    private KafkaConsumerConfiguration configuration;
    private Consumer<Long, String> consumer;

    @Override
    public void open() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getServerConfig());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, configuration.getOffsetResetEarlier());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IMessageDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IMessageDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, configuration.getPoolRecord());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, configuration.getOffsetResetEarlier());
        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(configuration.getTopic()));
        this.onOpen();
    }

    @Override
    public void close() {
        consumer.close();
        this.onStop();
    }

    @Override
    protected List<IData> onConsume() throws ConsumerException {
        List<IData> messages = new ArrayList<>();
        ConsumerRecords<Long, String> poll = consumer.poll(Integer.parseInt(configuration.getPoolRecord()));
        Iterable<ConsumerRecord<Long, String>> records = poll.records(configuration.getTopic());

        for (ConsumerRecord<Long, String> i : records) {
            IData message = (IData) new Gson().fromJson(i.value(), getClazz());
            messages.add(message);
        }

        return messages;
    }

    private Class getClazz() throws ConsumerException {
        String dataClazz = configuration.getDataClazz();
        try {
            return Class.forName(dataClazz);
        } catch (ClassNotFoundException e) {
            throw new ConsumerException("", e, configuration.getName());
        }
    }

    @Override
    public void configure(String path) throws ConsumerException {
        String configuration;
        try {
            configuration = OrchestrationUtil.readFile(path);
        } catch (OrchestrationException e) {
            throw new ConsumerException("File read exception while consumer ", e, this.configuration.getName());
        }
        this.configuration = new Gson().fromJson(configuration, KafkaConsumerConfiguration.class);
    }
}
