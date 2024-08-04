package src.commands;

import commands.SaveCommand;
import exception.CommandNotAcceptArgumentsException;
import utility.CollectionManager;

import java.io.Serializable;

/**
 * The type Info command.
 */
public class InfoCommand implements Command, CommandClient, Serializable {
    private static final long serialVersionUID = 2L;
    private final SaveCommand saveCommand;
    private CollectionManager collectionManager;


    /**
     * Instantiates a new Info command.
     *
     * @param collectionManager the collection manager
     * @param saveCommand       the save command
     */
    public InfoCommand(CollectionManager collectionManager, SaveCommand saveCommand){
        this.collectionManager = collectionManager;
        this.saveCommand = saveCommand;
    }

    @Override
    public String execute(String argument) {
        try {
            if (!argument.isEmpty()){
                throw new CommandNotAcceptArgumentsException();
            }
            String answer = "";

            answer += "Сведения о коллекции:" + '\n';
            answer += "Тип: " + collectionManager.getLabWorkVector().getClass().getSimpleName() + "\n";
            answer += "Дата инициализации: " + collectionManager.getLastInitTime() + "\n";
            String saveTime = "";
            if (saveCommand.saveTimeCollection() == null) {
                saveTime = "Коллекция не сохранялась в этой сессии";
            } else saveTime = saveCommand.saveTimeCollection().toString();
            answer += "Дата сохранения: " + saveTime + "\n";
            answer += "Количество элементов: " + collectionManager.getLabWorkVector().size() + "\n";
            return  answer;
            }
        catch (CommandNotAcceptArgumentsException e){
            return  "Этой команде не нужны аргументы!";
        }
    }


    @Override
    public String getName() {
        return "info";
    }


    @Override
    public String getDescription() {
        return "команда, выводящая в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.";
    }
}
