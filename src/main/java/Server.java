import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static ExecutorService executorService = Executors.newFixedThreadPool(2);


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        int clientNumber = 0;
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client accepted " + ++clientNumber);

            executorService.execute(new ServerClientAcceptedHandler(clientSocket));
        }
    }

    static void notifyAllClients(List<Task> taskList) {
//        listClientSockets.forEach(socket -> {
//
//        });
    }
}
