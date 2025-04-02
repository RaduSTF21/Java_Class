package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class UpdateCommand implements Command {
    @Override
    public void execute(String[] args, Repository repository) throws CommandException {
        if (args.length < 5) {
            throw new CommandException("Usage: updateCommand <name> <new name(use old if now changed)> <date> <tags> <path> ");
        }
        try {
            String name = args[0];
            String newName = args[1];
            LocalDate newDate = LocalDate.parse(args[2]);
            var tags = Arrays.asList(args[3].split(","));
            var path = Paths.get(args[4]);
            if (!Files.exists(path)) {
                throw new CommandException("Path not found: " + path);
            }
            if (Files.isDirectory(path)) {
                throw new CommandException("Path is a directory: " + path);
            }
            Image newImage = new Image(newName, newDate, tags, path);
            repository.updateImage(name, newImage);
        } catch (ImageNotFoundException e) {
            throw new CommandException("Image not found: " + args[0]);
        }
    }
}
