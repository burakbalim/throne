package throne.orchestration.common;

public abstract class FeederConfiguration implements ConfigurationBase {

    private String serverConfig;
    private String clientIdConfig;

    public String getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(String serverConfig) {
        this.serverConfig = serverConfig;
    }

    public String getClientIdConfig() {
        return clientIdConfig;
    }

    public void setClientIdConfig(String clientIdConfig) {
        this.clientIdConfig = clientIdConfig;
    }

}
