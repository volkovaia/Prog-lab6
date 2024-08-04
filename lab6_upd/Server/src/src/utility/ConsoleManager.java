package src.utility;

import exception.CommandNotFoundException;
import lombok.Data;

import java.io.Serializable;
import java.util.Scanner;

@Data
public class ConsoleManager implements Serializable {

    private Scanner scanner = new Scanner(System.in);

    CommandManager manager;

    private boolean isRunning = false;


    public ConsoleManager(CommandManager manager) {
        this.manager = manager;
        //manager.getCommands().put("execute_script", new ExecuteScriptCommand(this, new CollectionManager(new FileManager())));

    }



    public void StartCommandLoop() {

        isRunning = true;
        String in = "";
        while (isRunning) {
            if (scanner.hasNextLine()){
                in = scanner.nextLine();
                if (in.isBlank()){
                    continue;
            }
            }

            else
                break;


            try {
                manager.executeCommand(in);
            } catch (CommandNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}