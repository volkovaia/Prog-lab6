package src.commands;
//
//import exception.CommandNeedArgumentException;
//import exception.CommandNotAcceptArgumentsException;
//import utility.CollectionManager;
//
///**
// * Команда удаляет элемент из коллекции по id
// */
//public class RemoveById implements Command {
//    private CollectionManager collectionManager = JavaCollectionManager.getInstance();
//
//    @Override
//    public void execute(String argument) {
//        try {
//            if (argument.isEmpty()) throw new CommandNeedArgumentException();
//            try {
//                int id = Integer.parseInt(argument);
//                collectionManager.getLabWorkCollection().removeIf(route -> (route.getId() == id));
//                System.out.println("Удалён элемент по id: " + id);
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        } catch (CommandNotAcceptArgumentsException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public String getName() {
//        return null;
//    }
//
//    @Override
//    public String getDescription() {
//        return null;
//    }
//}

import exception.CommandNeedArgumentException;
import utility.CollectionManager;
import utility.ConsoleManager;

import java.io.Serializable;


/**
 * The type Remove by id command.
 */
public class RemoveByIdCommand implements Command, CommandClient, Serializable {
    private static final long serialVersionUID = 8L;
    /**
     * Instantiates a new Remove by id command.
     *
     * @param consoleManager    the console manager
     * @param collectionManager the collection manager
     */
    public RemoveByIdCommand(ConsoleManager consoleManager, CollectionManager collectionManager){
        this.collectionManager = collectionManager;
        this.consoleManager = consoleManager;
    }
    private CollectionManager collectionManager;
    private final ConsoleManager consoleManager;

    @Override
    public String execute(String argument) {
        try {
            if (argument.isEmpty()) throw new CommandNeedArgumentException("Это команде необходим аргумент типа int!");

            int id = Integer.parseInt(argument);
            try{
                if (!collectionManager.checkExist(id));
            } catch (NullPointerException e){
                return "Элемента с таким id не существует!";
            }
            collectionManager.getLabWorkVector().removeIf(labWork -> (labWork.getId() == id));
            return "Удалён элемент по id: " + argument;
        }catch (NumberFormatException e){
            return "id должен иметь тип int";
        }


    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "команда, удаляющая элемент из коллекции по его id. Команда ожидает аргумент типа int!";
    }
}