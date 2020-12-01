package client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    static ExecutorService executorService = Executors.newFixedThreadPool(2);
    static JFrame jFrame = new JFrame("Kanban");
    static ViewAllTask viewAllTask = new ViewAllTask();
    static EditTaskView editTaskView;
    static CreateTaskView createTaskView;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        jFrame.setPreferredSize(new Dimension(700, 400));
        jFrame.getContentPane().setLayout(new BorderLayout());
        jFrame.getContentPane().add(viewAllTask.getjScrollPane(), BorderLayout.WEST);
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
