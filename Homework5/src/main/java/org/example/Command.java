package org.example;

import java.awt.*;

public interface Command {
    void execute(String[] args, Repository repository) throws CommandException;
}
