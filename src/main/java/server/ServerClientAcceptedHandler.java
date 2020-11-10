package server;

import common.ClientRequest;
import common.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ServerClientAcceptedHandler implements Runnable {

	private static Socket clientSocket;
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;


	public ServerClientAcceptedHandler(Socket client) {
		ServerClientAcceptedHandler.clientSocket = client;
	}

	private void initializeStreams() throws IOException {
		objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
	}

	@Override
	public void run() {

		try {
			System.out.println("server.ServerClientAcceptedHandler started " + Thread.currentThread().getName());
			initializeStreams();

			sendTaskListToClient();

			while (!clientSocket.isClosed()) {
				handleClientRequest();
			}

			objectInputStream.close();
			objectOutputStream.close();
			clientSocket.close();
			System.out.println("server.ServerClientAcceptedHandler ended " + Thread.currentThread().getName());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void sendTaskListToClient() throws IOException {
		List<Task> taskListFromRepository = ServerTaskRepository.getTaskList();
		objectOutputStream.reset();
		objectOutputStream.writeObject(taskListFromRepository);
	}

	// TODO: think about ServerResponse
	private void handleClientRequest() throws IOException, ClassNotFoundException {
		ClientRequest clientRequest = (ClientRequest) objectInputStream.readObject();
		switch (clientRequest.getRequestType()) {
			case CREATE_TASK: {
				Task receivedTask = (Task) clientRequest.getData();
				ServerTaskRepository.addTaskToRepository(receivedTask);
				break;
			}
			case UPDATE_TASK: {
				Task receivedTask = (Task) clientRequest.getData();
				ServerTaskRepository.patchTaskInRepository(receivedTask);
				break;
			}
			case DELETE_TASK: {
				long taskId = (long) clientRequest.getData();
				ServerTaskRepository.deleteTaskFromRepository(taskId);
				break;
			}
			default: {
				System.out.println("Unknown request type!");
			}
		}

	}
}
