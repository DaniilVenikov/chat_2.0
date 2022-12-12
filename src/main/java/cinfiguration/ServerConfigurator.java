package cinfiguration;

import java.net.URI;

public interface ServerConfigurator {
    ServerConfiguration loadConfiguration(URI pathToConfiguration);
}
