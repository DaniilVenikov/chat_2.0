package configuration.exception;

public class ConfigurationNotFoundException extends RuntimeException{
    public ConfigurationNotFoundException() {
    }

    public ConfigurationNotFoundException(String message){
        super(message);
    }
}
