package configuration;

public class ServerConfigurationEntity implements ServerConfiguration{
    private int port;
    private String host;

    private ServerConfigurationEntity(Builder builder){
        this.host = builder.host;
        this.port = builder.port;
    }


    public static class Builder{
        private final int port;
        private final String host;

        public Builder(String host, int port)
        {
            this.host = host;
            this.port = port;
        }

        public ServerConfigurationEntity build(){
            return new ServerConfigurationEntity(this);
        }
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public String toString() {
        return "ServerConfigurationEntity{" +
                "port=" + port +
                ", host='" + host + '\'' +
                '}';
    }
}
