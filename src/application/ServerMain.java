package application;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerMain extends Application {
	
	
	public static int clientnum = 0;
	public static String te;
	
	//thread를 효과적으로 관리
	public static ExecutorService threadPool;
	//
	public static Vector<Client> clients = new Vector<Client>();
	
	static ServerSocket serverSocket;
	
	
	//서버를 구동시켜서 클라이언트의 연결을 기다리는 메소드
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
		
			
		//클라이언트가 접속할때까지 계쏙 기다리는 쓰레드 입니다.	
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				while(true) {

					try {						
						Socket socket = serverSocket.accept();
						
						clients.add(new Client(socket));
						
						clientnum ++;

						System.out.println("Client Connection"
								+socket.getRemoteSocketAddress()
								+": " + Thread.currentThread().getName());
														
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

	//서버의 작동을 중지시키는 메소드
	public void stopServer()	{
		
		try {
			//현재 작동 중인 모든 소켓 닫기
			Iterator<Client> iterator = clients.iterator();
			while(iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			//서버 소켓 객체 닫기
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			//threadpool 종료하기
			if(threadPool != null && !threadPool.isShutdown()) {
				threadPool.shutdown();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
	public static Connection connect() {	
		String user = "root";
		String password = "test";
		String url="jdbc:mysql://localhost:3306/game";
	
		Connection conn = null;
	
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,user,password);
			System.out.println("ok");
		}catch(ClassNotFoundException e) {
			System.out.println("fail");
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("fail");
		}
			return conn;
		}

	

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
//		connect();

		
		
		try {
			
		primaryStage.setTitle("Server page");
		Parent root = FXMLLoader.load(getClass().getResource("ServerMain.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}


}
