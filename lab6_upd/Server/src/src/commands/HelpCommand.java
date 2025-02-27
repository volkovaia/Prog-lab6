package src.commands;

import exception.CommandNotFoundException;
import utility.CommandManager;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * The type Help command.
 */
public class HelpCommand implements CommandClient, Command, Serializable {
    private static final long serialVersionUID = 1L;
    private transient CommandManager commandManager;

    /**
     * Instantiates a new Help command.
     *
     * @param commandManager the command manager
     */
    public HelpCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public String execute(String argument) {
        Stream<String> stream = commandManager.getCommands().keySet().stream()
                .map((key) -> {
                    try {
                        if (!Objects.equals(key, "save")){
                            return key + "\n" + commandManager.showDescription(key) + "\n\n";
                        } else {
                            return "";
                        }
                    } catch (CommandNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
        String answer = stream.collect(Collectors.joining(""));
        //System.out.println(answer);
        return answer;
    }




    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {

        return ("Команда, выводящая справку по всем доступным командам");
    }
}