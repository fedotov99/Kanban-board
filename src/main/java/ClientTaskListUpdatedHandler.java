import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientTaskListUpdatedHandler implements Runnable {
	private static Socket clientSocket;

	public ClientTaskListUpdatedHandler(Socket client) {
		ClientTaskListUpdatedHandler.clientSocket = client;
	}

	@Override
	public void run() {

//		try {
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}

	}
}
