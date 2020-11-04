import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientSocketOpenedHandler implements Runnable {
	private static Socket clientSocket;
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	List<Task> localCachedTaskList;

	public ClientSocketOpenedHandler(Socket client) {
		ClientSocketOpenedHandler.clientSocket = client;
	}

	private void initializeStreams() throws IOException {
		objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
	}

	@Override
	public void run() {

		try {
			System.out.println("ClientSocketOpenedHandler started " + Thread.currentThread().getName());
			initializeStreams();

			receiveTaskListFromServer();
			System.out.println("Client received tasks: \n");
			printLocalCachedTaskList();

			int count = 0;
			while (!clientSocket.isClosed() & count != 5) {
				Task task = new Task();
				task.setDescription("My first task");
				task.setPriority(70);
				postTaskOnServer(task);
				count++;
			}
			System.out.println("I sent 5 tasks to server");

			receiveTaskListFromServer();
			System.out.println("Client received tasks: \n");
			printLocalCachedTaskList();

			objectInputStream.close();
			objectOutputStream.close();
			clientSocket.close();
			System.out.println("ClientSocketOpenedHandler ended " + Thread.currentThread().getName());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void receiveTaskListFromServer() throws IOException, ClassNotFoundException {
		List<Task> receivedTaskList = (List<Task>)objectInputStream.readObject();
		localCachedTaskList = receivedTaskList;
	}

	private void printLocalCachedTaskList() {
		localCachedTaskList.forEach(task -> System.out.println(task.toString()));
	}

	private void postTaskOnServer(Task task) throws IOException{
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setData(task);
		clientRequest.setRequestType(RequestType.CREATE_TASK);
		objectOutputStream.writeObject(clientRequest);
	}

	private void patchTaskOnServer(Task task) throws IOException{
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setData(task);
		clientRequest.setRequestType(RequestType.UPDATE_TASK);
		objectOutputStream.writeObject(clientRequest);
	}

	private void deleteTaskOnServer(String id) throws IOException {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setData(id);
		clientRequest.setRequestType(RequestType.DELETE_TASK);
		objectOutputStream.writeObject(clientRequest);
	}
}
