import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientAcceptedHandler implements Runnable {

	private static Socket clientSocket;

	public ServerClientAcceptedHandler(Socket client) {
		ServerClientAcceptedHandler.clientSocket = client;
	}

	@Override
	public void run() {

		try {
			System.out.println("ServerClientAcceptedHandler started " + Thread.currentThread().getName());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			objectOutputStream.writeObject(TaskRepository.getTaskList());

			ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
			int count = 0;
			while (!clientSocket.isClosed() & count != 5) {
				Task receivedTask = (Task) objectInputStream.readObject();
				TaskRepository.addTaskToRepository(receivedTask);
				System.out.println(receivedTask.toString());
				count++;
			}

			objectInputStream.close();
			objectOutputStream.close();
			clientSocket.close();
			System.out.println("ServerClientAcceptedHandler ended " + Thread.currentThread().getName());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
