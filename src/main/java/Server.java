import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        List<Task> taskList = new LinkedList<>();

        int clientNumber = 0;
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client accepted " + ++clientNumber);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(taskList);

            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            Task receivedTask = (Task) objectInputStream.readObject();
            taskList.add(receivedTask);
            System.out.println(receivedTask.toString());

            objectInputStream.close();
            objectOutputStream.close();
            clientSocket.close();

        }
    }
}
