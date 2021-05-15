package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Client {
	
	public static int num = 0;
	public String player1;
	public String player2;

	Socket socket;
		
	public Client(Socket socket) {

		this.socket = socket;
		receive();
	}
	
	
	private void receive() {
		Runnable thread = new Runnable() {
			@Override
			public void run() {
				try {
					//
					while(true) {							
						InputStream in = socket.getInputStream();
						byte[] buffer = new byte[512];
						int length = in.read(buffer);
						while(length == -1) throw new IOException();
											
//						System.out.println("[Success]"
//								+socket.getRemoteSocketAddress()
//								+ ": " + Thread.currentThread().getName());

						
						String temp = socket.getRemoteSocketAddress().toString();
						String te = temp.substring(11,temp.length());
						
						String message = new String(buffer, 0, length, "UTF-8");
//						System.out.println(serverController.clientnum+"Client.java");

						if(message.compareTo("CONNECT") == 0) {
							num++;
							serverController.clients.get(num-1).send(te);
							
							if(num % 2 == 0) {
								serverController.clients.get(num-2).send(te);
							}
		
						}else {		
							for(Client client: serverController.clients) {
								client.send(te+message);
							}						
						}}
					
				}catch(Exception e) {
					try {
						
//						serverController.clients.get(0).socket.close();
						
						System.out.println("[receive error]"+
								socket.getRemoteSocketAddress()
						+ ": " + Thread.currentThread().getName());

//						String temp = socket.getRemoteSocketAddress().toString();
//						String te = temp.substring(11,temp.length());
//						
//						for(Client client :serverController.clients) {
//							client.send(te+"end");
//						}
						
					}catch(Exception e2) {
						e2.printStackTrace();
					}
				}
			}
			
		};
		serverController.threadPool.submit(thread);
	}
	
	//í�´ë�¼ì�´ì–¸íŠ¸ì—�ê²Œ ë©”ì„¸ì§€ë¥¼ ì „ì†¡í•˜ëŠ” ë©”ì†Œë“œìž…ë‹ˆë‹¤.
	public void send(String message) {
		Runnable thread = new Runnable(){

			@Override
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer = message.getBytes("UTF-8");					
					out.write(buffer);
					out.flush();
					
					}catch(Exception e) {
						try {
							System.out.println("[send Error]"
									+socket.getRemoteSocketAddress()
									+ ": " + Thread.currentThread().getName());
					
							serverController.clients.remove(Client.this);					
							socket.close();

							num--;
							
						}catch(Exception e2) {
							e2.printStackTrace();
						}
				}
			}
			
		};
		serverController.threadPool.submit(thread);
		
	}

}
