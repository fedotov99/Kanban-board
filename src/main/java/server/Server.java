package server;

import common.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static ExecutorService executorService = Executors.newFixedThreadPool(2);
    static List<ServerClientAcceptedHandler> handlerList = new LinkedList<>();


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        int clientNumber = 0;
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("server.Server started");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("client.Client accepted " + ++clientNumber);

            ServerClientAcceptedHandler newServerClientAcceptedHandler = new ServerClientAcceptedHandler(clientSocket);
            handlerList.add(newServerClientAcceptedHandler);
            executorService.execute(newServerClientAcceptedHandler);
        }
    }

    static void notifyAllClients(List<Task> taskList) {
        handlerList.forEach(handler -> {
            try {
                handler.sendTaskListToClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
