package client;

import common.Task;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    static ExecutorService executorService = Executors.newFixedThreadPool(2);
//    static Board board = new Board();
    static TaskView taskView = new TaskView();
    static ViewAllTask viewAllTask = new ViewAllTask();

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        JFrame jFrame = new JFrame("Kanban");
        jFrame.setContentPane(viewAllTask.getAllTasksPanel());
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);

        System.out.println("client.Client started");
        Socket clientSocket = new Socket("127.0.0.1", 8080);

        executorService.execute(new ClientSocketOpenedHandler(clientSocket));
        executorService.execute(new ClientSocketInputListener(clientSocket));

        System.out.println("client.Client ended");
    }

}
