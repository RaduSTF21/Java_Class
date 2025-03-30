package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class LoadCommand implements Command {

    @Override
    public void execute(String[] args, Repository repository) throws CommandException {
    if (args.length != 1) {
        throw new CommandException("Usage: load <filename>");
    }
    String filename = args[0];
    ObjectMapper objectMapper = new ObjectMapper();
    try {
        Image[] images = objectMapper.readValue(new File(filename), Image[].class);
        List<Image> imageList = Arrays.asList(images);
        repository.setImages(imageList);
        System.out.println("Loaded " + imageList.size() + " images from " + filename);
    }
    catch (Exception e) {
        throw new CommandException("Error loading repository from " + filename);
    }
    }
}
