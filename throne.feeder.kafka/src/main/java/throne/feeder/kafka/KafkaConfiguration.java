package throne.feeder.kafka;

import throne.orchestration.common.FeederConfiguration;

public class KafkaConfiguration extends FeederConfiguration {

    private String name;
    private String topic;
    private String messageCount;
    private String groupIdConfig;
    private String maxNoMessageFoundCount;
    private String offsetRestLatest;
    private String offsetRestEarlier;
    private String maxPollRecords;

    public String getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }

    public String getGroupIdConfig() {
        return groupIdConfig;
    }

    public void setGroupIdConfig(String groupIdConfig) {
        this.groupIdConfig = groupIdConfig;
    }

    public String getMaxNoMessageFoundCount() {
        return maxNoMessageFoundCount;
    }

    public void setMaxNoMessageFoundCount(String maxNoMessageFoundCount) {
        this.maxNoMessageFoundCount = maxNoMessageFoundCount;
    }

    public String getOffsetRestLatest() {
        return offsetRestLatest;
    }

    public void setOffsetRestLatest(String offsetRestLatest) {
        this.offsetRestLatest = offsetRestLatest;
    }

    public String getOffsetRestEarlier() {
        return offsetRestEarlier;
    }

    public void setOffsetRestEarlier(String offsetRestEarlier) {
        this.offsetRestEarlier = offsetRestEarlier;
    }

    public String getMaxPollRecords() {
        return maxPollRecords;
    }

    public void setMaxPollRecords(String maxPollRecords) {
        this.maxPollRecords = maxPollRecords;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return null;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
