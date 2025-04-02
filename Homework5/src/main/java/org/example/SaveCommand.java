package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class SaveCommand implements Command {
    @Override
    public void execute(String[] args, Repository repo) {
        if (args.length < 1) {
            System.out.println("Usage: save <file path>");
            return;
        }
        String path = args[0];
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), repo);
            System.out.println("Repository saved successfully to " + path);
        } catch (IOException e) {
            System.out.println("Error saving repository: " + e.getMessage());
        }
    }
}
