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
			System.out.println("ServerClientAcceptedHandler started " + Thread.currentThread().getName());
			initializeStreams();

			sendTaskListToClient();

			int count = 0;
			while (!clientSocket.isClosed() && count != 5) {
				handleClientRequest();
				count++;
			}
			sendTaskListToClient();

			objectInputStream.close();
			objectOutputStream.close();
			clientSocket.close();
			System.out.println("ServerClientAcceptedHandler ended " + Thread.currentThread().getName());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void sendTaskListToClient() throws IOException {
		List<Task> taskListFromRepository = TaskRepository.getTaskList();
		objectOutputStream.reset();
		objectOutputStream.writeObject(taskListFromRepository);
	}

	// TODO: think about ServerResponse
	private void handleClientRequest() throws IOException, ClassNotFoundException {
		ClientRequest clientRequest = (ClientRequest) objectInputStream.readObject();
		switch (clientRequest.getRequestType()) {
			case CREATE_TASK: {
				Task receivedTask = (Task) clientRequest.getData();
				TaskRepository.addTaskToRepository(receivedTask);
				break;
			}
			case UPDATE_TASK: {
				Task receivedTask = (Task) clientRequest.getData();
				TaskRepository.patchTaskInRepository(receivedTask);
				break;
			}
			case DELETE_TASK: {
				String taskId = (String) clientRequest.getData();
				TaskRepository.deleteTaskFromRepository(taskId);
				break;
			}
			default: {
				System.out.println("Unknown request type!");
			}
		}

	}
}
