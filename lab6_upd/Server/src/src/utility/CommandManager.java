package src.utility;

import commands.Command;
import exception.CommandNeedArgumentException;
import exception.CommandNotFoundException;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;

@Getter
public class CommandManager implements Serializable {

    @Getter
    private final HashMap<String, Command> commands = new HashMap<>();


    public CommandManager(){

    }



    public String executeCommand(String stringCommand) throws CommandNotFoundException {

        String[] commandSplit = stringCommand.split(" ", 2);

        String commandName = commandSplit[0];

        String commandArguments = "";


        if (commandSplit.length == 2) {
            commandArguments = commandSplit[1];
        }

        Command command = commands.get(commandName);


        if (command == null) {
            throw new CommandNotFoundException(commandName);
        }
        try {
            String answer = command.execute(commandArguments);
            return answer;
        } catch (CommandNeedArgumentException e) {
            return e.getMessage();
        }
    }

    public String showDescription(String stringCommand) throws CommandNotFoundException {

        String[] commandSplit = stringCommand.split(" ", 2);

        String commandName = commandSplit[0];

        String commandArguments = "";


        if (commandSplit.length == 2) {
            commandArguments = commandSplit[1];
        }

        Command command = commands.get(commandName);


        if (command == null) {
            throw new CommandNotFoundException(commandName);
        }
        try {
            String answer = command.getDescription();
            return answer;
        } catch (CommandNeedArgumentException e) {
            return e.getMessage();
        }
    }
}