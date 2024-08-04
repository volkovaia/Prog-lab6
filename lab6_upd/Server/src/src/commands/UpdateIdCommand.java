package src.commands;

import utility.CollectionManager;

import java.io.Serializable;

/**
 * The type Update id command.
 */
public class UpdateIdCommand implements Command, CommandClient, Serializable {
    private static final long serialVersionUID = 13L;
    private final CollectionManager collectionManager;

    /**
     * Instantiates a new Update id command.
     *
     * @param collectionManager the collection manager
     */
    public UpdateIdCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;

    }
    @Override
    public String execute(String argument) {
        try {
            collectionManager.updateByIdCollection(argument);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Команда успешно обновлена";
    }
    @Override
    public String getName() {
        return "update_by_id";
    }

    @Override
    public String getDescription() {
        //removeIf
        return "команда, обновить значение элемента коллекции, id которого равен заданному. Команда ожидает аргумент типа int!";
    }
}