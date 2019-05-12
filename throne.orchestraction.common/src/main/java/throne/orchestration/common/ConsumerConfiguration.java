package throne.orchestration.common;

public abstract class ConsumerConfiguration implements ConfigurationBase {

    private String serverConfig;
    private String dataClazz;

    public String getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(String serverConfig) {
        this.serverConfig = serverConfig;
    }

    public String getDataClazz() {
        return dataClazz;
    }

    public void setDataClazz(String dataClazz) {
        this.dataClazz = dataClazz;
    }
}
