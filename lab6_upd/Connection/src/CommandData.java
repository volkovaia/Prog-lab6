//import commands.AcceptInteger;

import exception.CommandNeedArgumentException;
import exception.CommandNotFoundException;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommandData implements Serializable {

    private static final long serialVersionUID = 132454324L;

    private String command;
    private String args = "";

    public CommandData(String input) throws CommandNotFoundException {
        String[] commandSplit = input.split(" ", 2);

        String commandName = commandSplit[0];

        String commandArguments = "";


        if (commandSplit.length == 2) {
            commandArguments = commandSplit[1];
        }

        try {
            String command = commandName;
            if (command == null ) {
                throw new CommandNotFoundException(commandName);
            }
            this.command = commandName;
            this.args = commandArguments;

        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
        //System.out.println(command.getClass());
    }
    public String returnArgs(){
        return args;
    }
    public String returnCommand(){

        return this.command;
    }


    //вызывает интерфейс и проверяет на корректность введённых данных
}
