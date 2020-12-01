package client;

import common.ClientRequest;
import common.RequestType;
import common.Task;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class ClientSocketOpenedHandler implements Runnable {
	private static Socket clientSocket;
	static ObjectOutputStream objectOutputStream;

	public ClientSocketOpenedHandler(Socket client) {
		ClientSocketOpenedHandler.clientSocket = client;
	}

	private void initializeStreams() throws IOException {
		objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
	}

	@Override
	public void run() {

		try {
			System.out.println("client.ClientSocketOpenedHandler started " + Thread.currentThread().getName());
			initializeStreams();

			System.out.println("client.ClientSocketOpenedHandler ended " + Thread.currentThread().getName());
		} catch (IOException  e) {
			e.printStackTrace();
		}
	}

	public static void postTaskOnServer(Task task) throws IOException{
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setData(task);
		clientRequest.setRequestType(RequestType.CREATE_TASK);
		objectOutputStream.reset();
		objectOutputStream.writeObject(clientRequest);
	}

	public static void patchTaskOnServer(Task task) throws IOException{
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setData(task);
		clientRequest.setRequestType(RequestType.UPDATE_TASK);
		objectOutputStream.reset();
		objectOutputStream.writeObject(clientRequest);
	}

	public static void deleteTaskOnServer(UUID id) throws IOException {
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setData(id);
		clientRequest.setRequestType(RequestType.DELETE_TASK);
		objectOutputStream.reset();
		objectOutputStream.writeObject(clientRequest);
	}
}
