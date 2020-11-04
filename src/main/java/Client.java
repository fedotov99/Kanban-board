import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("Client started");
        Socket clientSocket = new Socket("127.0.0.1", 8080);

        executorService.execute(new ClientSocketOpenedHandler(clientSocket));

        System.out.println("Client ended");
    }

}
