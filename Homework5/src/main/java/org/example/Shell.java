package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Shell{
    private final Map<String, Command> commands = new HashMap<>();
    private final Repository repository;

    public Shell(Repository repository) {
        this.repository = repository;
        commands.put("add", new AddCommand());
        commands.put("remove", new RemoveCommand());
        commands.put("update", new UpdateCommand());
        commands.put("load", new LoadCommand());
        commands.put("save", new SaveCommand());
        commands.put("report", new ReportCommand());
        commands.put("exit", (args,repo) -> System.exit(0));
    }
    public void start() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Image Repository Shell. Type 'exit' to exit the shell: ");
        while (true) {
            System.out.print("> ");
            try {


                String line = reader.readLine();
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                String[] tokens = line.trim().split("\\s+");
                String commandName = tokens[0].toLowerCase();
                Command command = commands.get(commandName);
                if (command == null) {
                    System.out.println("Unknown command: " + commandName);
                    continue;
                }
                String[] arguments = new String[tokens.length - 1];
                System.arraycopy(tokens, 1, arguments, 0, arguments.length);
                command.execute(arguments,repository);
            } catch (Exception e) {
                System.out.println("Error" + e.getMessage());

            }
        }
    }
}
