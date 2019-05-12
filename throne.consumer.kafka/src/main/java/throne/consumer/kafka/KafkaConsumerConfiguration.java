package throne.consumer.kafka;

import throne.orchestration.common.ConsumerConfiguration;

public class KafkaConsumerConfiguration extends ConsumerConfiguration {

    private String name;
    private String poolRecord;
    private String enableAutoCommit;
    private String topic;
    private String offsetResetEarlier;
    private String groupIDConfig;

    public String getPoolRecord() {
        return poolRecord;
    }

    public void setPoolRecord(String poolRecord) {
        this.poolRecord = poolRecord;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getEnableAutoCommit() {
        return enableAutoCommit;
    }

    public void setEnableAutoCommit(String enableAutoCommit) {
        this.enableAutoCommit = enableAutoCommit;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getOffsetResetEarlier() {
        return offsetResetEarlier;
    }

    public void setOffsetResetEarlier(String offsetResetEarlier) {
        this.offsetResetEarlier = offsetResetEarlier;
    }

    public String getGroupIDConfig() {
        return groupIDConfig;
    }

    public void setGroupIDConfig(String groupIDConfig) {
        this.groupIDConfig = groupIDConfig;
    }
}
