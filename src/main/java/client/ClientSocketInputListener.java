package client;

import common.ClientRequest;
import common.RequestType;
import common.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientSocketInputListener implements Runnable {
	private static Socket clientSocket;
	ObjectInputStream objectInputStream;

	public ClientSocketInputListener(Socket client) {
		ClientSocketInputListener.clientSocket = client;
	}

	private void initializeStreams() throws IOException {
		objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
	}

	@Override
	public void run() {

		try {
			System.out.println("client.ClientSocketInputListener started " + Thread.currentThread().getName());
			initializeStreams();

			while (!clientSocket.isClosed()) {
				receiveTaskListFromServer();
				System.out.println("client.Client received tasks: ");
				printLocalCachedTaskList();
			}

			objectInputStream.close();
			clientSocket.close();
			System.out.println("client.ClientSocketOpenedHandler ended " + Thread.currentThread().getName());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void receiveTaskListFromServer() throws IOException, ClassNotFoundException {
		List<Task> receivedTaskList = (List<Task>)objectInputStream.readObject();
		ClientLocalCachedTaskRepository.setLocalCachedTaskList(receivedTaskList);
	}

	private void printLocalCachedTaskList() {
		ClientLocalCachedTaskRepository
				.getLocalCachedTaskList()
				.forEach(task -> System.out.println(task.toString()));
	}
}
