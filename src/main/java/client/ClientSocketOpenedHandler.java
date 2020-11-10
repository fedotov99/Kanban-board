package client;

import common.ClientRequest;
import common.RequestType;
import common.Task;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.management.ManagementFactory;
import java.net.Socket;

public class ClientSocketOpenedHandler implements Runnable {
	private static Socket clientSocket;
	static ObjectOutputStream objectOutputStream;
//	ObjectInputStream objectInputStream;

	public ClientSocketOpenedHandler(Socket client) {
		ClientSocketOpenedHandler.clientSocket = client;
	}

	private void initializeStreams() throws IOException {
		objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//		objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
	}

	@Override
	public void run() {

		try {
			System.out.println("client.ClientSocketOpenedHandler started " + Thread.currentThread().getName());
			initializeStreams();

			int count = 0;
//			while (!clientSocket.isClosed()) {
//				Task task = new Task();
//				task.setDescription("My task number " + count + " from thread " + ManagementFactory.getRuntimeMXBean().getName());
//				task.setPriority(70 + count);
//				postTaskOnServer(task);
//				count++;
//				Thread.sleep(5000);
////				if (count == 5) {
////					System.out.println("I sent 5 tasks to server");
////					Thread.sleep(5000);
////					count = 0;
////				}
//			}

//			objectOutputStream.close();
//			clientSocket.close();
			System.out.println("client.ClientSocketOpenedHandler ended " + Thread.currentThread().getName());
//		} catch (IOException | InterruptedException e) {
		} catch (IOException  e) {
			e.printStackTrace();
		}
	}

	public static void postTaskOnServer(Task task) throws IOException{
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
