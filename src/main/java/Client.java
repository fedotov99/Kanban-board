import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Client started");
        Socket clientSocket = new Socket("127.0.0.1", 8080);
//        Thread.sleep(60000);

        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        List<Task> receivedTaskList = (List<Task>)objectInputStream.readObject();
        System.out.println("Client received tasks: \n");
        receivedTaskList.forEach(task -> System.out.println(task.toString()));
//        objectInputStream.close();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        int count = 0;
        while (!clientSocket.isClosed() & count != 5) {
            Task task = new Task();
            task.setDescription("My first task");
            task.setPriority(70);
            objectOutputStream.writeObject(task);
            count++;
        }
        System.out.println("I sended 5 tasks!");

        objectInputStream.close();
        objectOutputStream.close();
        clientSocket.close();
        System.out.println("Client ended");
    }

}
