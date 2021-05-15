package application;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;


public class serverController implements Initializable {

	@FXML
	public TextArea teArea;
	@FXML
	public ToggleButton toBtn;
	public static int clientnum = 0;
	public static String te;
	
	//threadë¥¼ íš¨ê³¼ì �ìœ¼ë¡œ ê´€ë¦¬
	public static ExecutorService threadPool;
	
	//
	public static Vector<Client> clients = new Vector<Client>();
	
	static ServerSocket serverSocket;
	
	public void startServer(String IP, int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(IP, port));
			
		}catch(Exception e) {
			e.printStackTrace();
			if(!serverSocket.isClosed()) 
				stopServer();
				return;
		}
		
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {						
						Socket socket = serverSocket.accept();
						clients.add(new Client(socket));
						
//						clientnum = clients.size();
//						System.out.println(clientnum);
//						
						System.out.println("Client Connection"
								+socket.getRemoteSocketAddress()
								+": " + Thread.currentThread().getName());
//						
						
					}catch(Exception e) {
						if(!serverSocket.isClosed()) 
							stopServer();
						
						break;
						
					}
				}
			}
		};
		threadPool = Executors.newCachedThreadPool();
		threadPool.submit(thread);
		
	}

	public void stopServer()	{
		
		try {
			Iterator<Client> iterator = clients.iterator();
			while(iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			if(threadPool != null && !threadPool.isShutdown()) {
				threadPool.shutdown();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		String IP = "127.0.01";
		int port = 9876;
		
		toBtn.setOnAction(event ->{
			if(toBtn.getText().equals("start")) {
				startServer(IP, 9876);
				
				Platform.runLater(()->{
					String message = String.format("[SERVER START]\n", IP,port);
					teArea.appendText(message);
					toBtn.setText("end");
					
				});
			}else {
				stopServer();
				Platform.runLater(()->{
					String message = String.format("[SERVER STOP]\n", IP, port);
					teArea.appendText(message);
					toBtn.setText("start");
					
					clients.clear();
					Client.num = 0;
					
				});
			}
		});
	}
	
	

}
