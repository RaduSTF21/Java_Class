package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class LoadCommand implements Command {
    @Override
    public void execute(String[] args, Repository repo) {
        if (args.length < 1) {
            System.out.println("Usage: load <file path>");
            return;
        }
        String path = args[0];
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {

            Repository loadedRepo = mapper.readValue(new File(path), Repository.class);
            repo.setImages(loadedRepo.getImages());
            System.out.println("Repository loaded successfully from " + path);

            System.out.println(loadedRepo);
        } catch (IOException e) {
            System.out.println("Error loading repository: " + e.getMessage());
        }
    }
}
