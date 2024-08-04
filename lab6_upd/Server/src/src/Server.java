package src;// из байтов получить объект типа Command





// Java program to illustrate Server side
// Implementation using DatagramSocket

import commands.*;
import exception.CommandNotFoundException;

import utility.CollectionManager;
import utility.CommandManager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Scanner;

import utility.ConsoleManager;
import utility.FileManager;

public class Server
{
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CommandManager commandManager = new CommandManager();



        FileManager fileManager = new FileManager("C:\\Users\\Ирина\\IdeaProjects\\lab6\\Server\\json\\file_new.json");
        CollectionManager collectionManager = new CollectionManager(fileManager);
        ConsoleManager consoleManager = new ConsoleManager(commandManager);
        HashMap<String, Command> commands = commandManager.getCommands();
        commands.put("save", new SaveCommand(fileManager, collectionManager));
        commands.put("clear", new ClearCommand(collectionManager));
        commands.put("info", new InfoCommand(collectionManager, new SaveCommand(fileManager, collectionManager)));
        commands.put("filter_less_than_personal_qualities_minimum", new FilterLessThanPersonalQualitiesMinimumCommand(collectionManager));
        commands.put("print_field_ascending_discipline", new PrintFieldAscendingPersonalQualitiesMinimumCommand(collectionManager));
        commands.put("remove_by_id", new RemoveByIdCommand(consoleManager, collectionManager));
        commands.put("remove_all_by_personal_qualities_minimum", new RemoveByPersonalQualitiesMinimumCommand(collectionManager));
        commands.put("sort", new SortCommand(collectionManager));
        commands.put("add", new AddCommand(collectionManager));
        commands.put("show", new ShowCommand(collectionManager));
        commands.put("shuffle", new ShuffleCommand(collectionManager));
        commands.put("add_if_min", new AddIfMinCommand(collectionManager));
        commands.put("update_by_id", new UpdateIdCommand(collectionManager));
        commands.put("help", new HelpCommand(commandManager));
        commands.put("exit", new ExitCommand());
        //HelpCommand helpCommand = new HelpCommand(commandManager);
        //helpCommand.execute("help");
        collectionManager.loadCollection();
        //consoleManager.StartCommandLoop();


        // Step 1 : Create a socket to listen at port 1234
        DatagramSocket datagramSocket = new DatagramSocket(1234);
        InetAddress serverAddress = InetAddress.getByName("localhost");
        String hostName = serverAddress.getHostName();
//        System.out.println("Host name: " + hostName);
//        System.out.println("IP address: " + serverAddress.getHostAddress());
//        System.out.println("InetAddress:" + serverAddress);

        //String serverInput = "";

        Thread myThread1 = new Thread(new Runnable() {
            public void run() {
                while (scanner.hasNextLine()) {
                     String serverInput = scanner.nextLine();
                    if (serverInput.equals("save")) {
                        commands.get("save");
                        System.out.println("Коллекция успешно сохранена!");
                    }
                }
            }
        });
        myThread1.start();

        byte[] receive = new byte[65535];
        byte[] sendData = new byte[65535];

        DatagramPacket DpReceive = null;

        while (true)
        {
            DpReceive = new DatagramPacket(receive, receive.length);
            datagramSocket.receive(DpReceive);
            ByteArrayInputStream bis = new ByteArrayInputStream(DpReceive.getData());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object deserializedObject = ois.readObject();
            if (deserializedObject instanceof CommandData) {
                //System.out.println("Является типом CommandData");
                CommandData data = (CommandData)deserializedObject;
                InetAddress clientAddress = DpReceive.getAddress();
                int clientPort = DpReceive.getPort();


                System.out.println(data.returnCommand().getName());
                if (data.returnCommand().getName().equals("exit")) {
                    commands.get("save");
                    //System.out.println("saved");
                }
                try {
                    String serverResponse = commandManager.executeCommand(data.returnCommand().getName() + " " + data.returnArgs());
                    //System.out.println(serverResponse);
                    sendData = serverResponse.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    datagramSocket.send(sendPacket);

                } catch (CommandNotFoundException e){
                    System.out.println("Команда не найдена");

                }
            } else {
                System.out.println("не понял");
            }
//            if (data(receive).toString().equals("exit"))
//            {
//                System.out.println("Client sent bye.....EXITING");
//                break;
//            }
            receive = new byte[65535];
        }
    }

    // A utility method to convert the byte array
    // data into a string representation.
    public static StringBuilder data(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }





}
