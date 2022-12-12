package cinfiguration;

import configuration.exception.ConfigurationNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Scanner;

public class FileSystemServerConfigurator implements ServerConfigurator{
    @Override
    public ServerConfiguration loadConfiguration(URI pathToConfiguration){
        String pathToFile = pathToConfiguration.getPath();
        File file = new File(pathToFile);
        System.out.println(file.toURI());
        try (Scanner fileReader = new Scanner(file)) {
            String host = fileReader.next();
            int port = fileReader.nextInt();
            return new ServerConfigurationEntity.Builder(host,port).build();
        } catch (FileNotFoundException e) {
            throw new ConfigurationNotFoundException("File with configuration not found");
        }
    }
}
