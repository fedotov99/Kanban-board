import java.io.*;
import java.net.Socket;
import java.util.List;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Client started");
        Socket clientSocket = new Socket("127.0.0.1", 8080);

        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        List<Task> receivedTaskList = (List<Task>)objectInputStream.readObject();
        System.out.println("Client received tasks: \n");
        receivedTaskList.forEach(task -> System.out.println(task.toString()));
//        objectInputStream.close();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        Task task = new Task();
        task.setDescription("My first task");
        task.setPriority(70);
        objectOutputStream.writeObject(task);

        objectInputStream.close();
        objectOutputStream.close();
        clientSocket.close();
    }

}
