package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class SaveCommand implements Command {
    @Override
    public void execute(String[] args, Repository repository) throws CommandException {
        if(args.length != 1)
        {
            throw new CommandException("Usage : save <filename>");
        }
        String filename = args[0];
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), repository.getImages());
            System.out.println("Saved images to " + filename);
        }
        catch (Exception e) {
            throw new CommandException("Error saving repo to " + filename);
        }
    }

}
