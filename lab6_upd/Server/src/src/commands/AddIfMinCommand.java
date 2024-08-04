package src.commands;

import data.ComparatorLabwork;
import data.LabWork;
import utility.CollectionManager;

import java.io.Serializable;
import java.util.*;

/**
 * The type Add if min command.
 */
public class AddIfMinCommand implements Command, CommandClient, Serializable {
    private static final long serialVersionUID = 4L;
    private CollectionManager collectionManager;

    /**
     * Instantiates a new Add if min command.
     *
     * @param collectionManager the collection manager
     */
    public AddIfMinCommand(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }


    @Override
    public String execute(String argument) {
        try {
            collectionManager.addIfMin(argument);
        }catch (Exception e){
            return e.getMessage();
        }
        return "Элемент добавлен в коллекцию";
    }

    @Override
    public String getName() {
        return "add_if_min";
    }

    @Override
    public String getDescription() {
        return "команда, добавляющая элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции. Команда ожидает аргумент числового типа";
    }
}
