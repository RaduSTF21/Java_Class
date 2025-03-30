package org.example;

public class RemoveCommand implements Command {
    @Override
    public void execute(String[] args, Repository repository) throws CommandException {
        if (args.length != 1) {
            throw new CommandException("Usage : remove <name>");
        }
        try {
            repository.removeImage(args[0]);
            System.out.println("Removed image " + args[0]);
            } catch (ImageNotFoundException e) {
                throw new CommandException("Image not found: " + args[0]);
        }
    }
}
