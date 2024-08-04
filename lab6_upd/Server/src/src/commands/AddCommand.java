package src.commands;

import utility.CollectionManager;

import java.io.Serializable;

/**
 * The type Add command.
 */
public class AddCommand implements Command, CommandClient, Serializable {
    private static final long serialVersionUID = 3L;
    /**
     * The Collection manager.
     */
    static CollectionManager collectionManager;

    /**
     * Instantiates a new Add command.
     *
     * @param collectionManager the collection manager
     */
    public AddCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;

    }


    @Override
    public String execute(String argument) {
        try {
//            StringBuilder sb = new StringBuilder(argument);
//            int index = sb.indexOf(" ");
//            String args = sb.substring(0, index);
            collectionManager.addFromInput(argument);
            return "Элемент добавлен в коллекцию!";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "команда, добавляющая новый элемент в коллекцию";
    }
}
