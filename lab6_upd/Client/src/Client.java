// Java program to illustrate Client side
// Implementation using DatagramSocket

import commands.*;
import exception.CommandNeedArgumentException;
import exception.CommandNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
//import Command



//надо, чтобы до того, как отправить на сервер, была проверка на корректность введённой команды

public class Client {
    public static void main(String args[]) throws IOException {


        Scanner sc = new Scanner(System.in);


        // Step 1:Create the socket object for
        // carrying the data.



        // loop while user not enters "exit"
        while (true) {
            String inp = sc.nextLine();
                CommandData commandData = null;
                try {
                    commandData = new CommandData(inp);

                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Команда не принимает аргументов...");
                    continue;
                } catch (NumberFormatException e) {
                    System.out.println("Команда ожидает другой тип аргументов...");
                    continue;
                } catch (CommandNeedArgumentException e) {
                    System.out.println("Команда ожидает строку!");
                    continue;
                } catch (CommandNotFoundException e) {
                    System.out.println("Нет такой команды...");
                    continue;
                } catch (NullPointerException e){
                    System.out.println("Вы ничего не ввели...");
                    continue;
                }


                byte buf[] = null;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(commandData);
                objectOutputStream.close();
                buf = byteArrayOutputStream.toByteArray();

//                System.out.println("пакет получен")

                try {
                    DatagramSocket clientSocket = new DatagramSocket();
                    //InetAddress serverAddress = InetAddress.getLocalHost();
                    InetAddress serverAddress = InetAddress.getByName("localhost");
                    clientSocket.connect(serverAddress, 1234);

                    byte[] receiveData = new byte [1000000];
                    //Отправка пакетов на сервер
                    DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, serverAddress, 1234);
                    clientSocket.send(sendPacket);


                    if (inp.equals("exit")) {
                        System.exit(0);
                    }

                    //Обработка ответов от сервера
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    System.out.println("Получено от сервера: \n" + serverResponse);

                    clientSocket.close();
                } catch (Exception e) {
                    System.out.println("Сервер временно недоступен.");
                }

            }
        }
    }

