package org.example;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class AddCommand implements Command {

    @Override
    public void execute(String[] args, Repository repository) throws CommandException {
        if(args.length < 4){
            throw new CommandException("Usage: add <name> <date(yyyy-MM-dd)> <tags(comma separated)> <path>");
        }
        try {
            String name = args[0];
            LocalDate date = LocalDate.parse(args[1]);
            var tags = Arrays.asList(args[2].split(","));
            var path = Paths.get(args[3]);
            Image image = new Image(name,date, tags,path);
            repository.addImage(image);
            System.out.println("Added image to repository");
        }
        catch (Exception e) {
            throw new CommandException("Error adding image: " + e.getMessage());
        }
    }
}

