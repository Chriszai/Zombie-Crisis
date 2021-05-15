package view;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.StarRaidersMainButton;

/**
 * Game menu page UI, client programs
 * @author Sungin Hwang
 *
 */


public class ViewManager {

	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	//game sound path
	private static final String Button_sound = "file:src/model/resources/button_sound.wav";
	public static final String start = "file:src/model/resources/start.wav";
	public static String backsound = "src/model/resources/backsound.wav";

	public static AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	public static Label logo;

	private final static int MENU_BUTTONS_START_X = 325;
	private final static int MENU_BUTTONS_START_Y = 250;

	private PlayScene playScene;
	private PlayScene2 playScene2;
	private netPlayScene netPlayScene;

	List<StarRaidersMainButton> menuButtons;
	
	private int hpcon = 100;
			
	public static Socket socket;
	public String direction;
	
	public static boolean player1 = true;
	//Username of the other player
	public static String otherplayer = null;
	//ready state for all players
	public static boolean ready = true;

	private static int first = 0;
	//my unique number on the network
	public String my;
	//unique number of the other player on the network
	public static String player = null;
	
	public Timeline timeline;
	public static Timeline timeline1;
	public static int hp2 = 100;
	
	//game over 
	public static boolean end = false;
	//stop connection
	public static boolean stop = false;
			
	public static AnimationTimer timer;
	

	/**
	 * drives the operation of the cilent
	 * @param IP ip to access
	 * @param port port number to connect to
	 */
	public void startClient(String IP, int port) {
		
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(IP, port);
					receive();
					
				}catch(Exception e) {
					
					if(socket != null) {
						if(!socket.isClosed()) {
							stopClient();
							Platform.exit();
					}}
				}
			}
		};thread.start();
	}
	
	/**
	 *  terminates the operation of the client
	 */
	public void stopClient() {
		try {
			if(socket != null && !socket.isClosed())
				socket.close();
		}catch( Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * receives messages from the server
	 */
	public void receive() {
		
		while(true) {
			try {
				InputStream in = socket.getInputStream();
				byte[] buffer = new byte[512];
				int length = in.read(buffer);
				if(length == -1) throw new IOException();
				String message = new String(buffer, 0,length,"UTF-8");
								
				if(first == 2 && message.contains(player)) {
					first++;
					otherplayer = message.substring(5, message.length());
				}
				
				if(first == 1 && message.length() == 5) {
					first++;
		
					player = message;
					player1 = true;
					
					Thread.sleep(1000);
					
					while(true) {
					if(matchController.username != null) {
						send(my + matchController.username);
						break;
					}}
				}
				
				if(first == 1 && message.length() >= 10) {
					first = first +2;
					player = message.substring(5,10);
					player1 = false;					
					
					while(true) {
						Thread.sleep(1000);

					if(matchController.username == null) {
						Thread.sleep(100);
					}else {
						otherplayer = message.substring(10,message.length());
						send(matchController.username);
						break;
					}}
				}

				if(first == 0) {
					first++;
					my = message;
				}
				
				
				Platform.runLater(()->{	
				if(player != null && message.contains(player)) {
					if(message.contains("pW")) {
						netPlayScene.playerView1.up = true;
					}else if(message.contains("pA")) {
						netPlayScene.playerView1.left = true;
					}else if(message.contains("pS")) {
						netPlayScene.playerView1.down = true;
					}else if(message.contains("pD")) {
						netPlayScene.playerView1.right = true;
					}else if(message.contains("rA")) {
						netPlayScene.playerView1.left = false;
						netPlayScene.playerView1.shootState = true;

					}else if(message.contains("rS")) {
						netPlayScene.playerView1.down = false;
						netPlayScene.playerView1.shootState = true;

					}else if(message.contains("rD")) {
						netPlayScene.playerView1.right = false;
						netPlayScene.playerView1.shootState = true;
						
					}else if(message.contains("rW")) {
						netPlayScene.playerView1.up = false;
						netPlayScene.playerView1.shootState = true;

					}
					
					if(message.contains("pJ")&& netPlayScene.playerView1.shootState == true) {
						
						int i = message.indexOf(":");
						
						double x = Double.parseDouble(message.substring(7,i));
						double y = Double.parseDouble(message.substring(i+1,message.length()));
						
						netPlayScene.playerView1.player.setxIndex(x);
						netPlayScene.playerView1.player.setyIndex(y);
						
						netPlayScene.playerView1.shoot(netPlayScene.playerView1.player);
						netPlayScene.playerView1.shootState = false;
				
					} 
					if(message.contains("rJ")) {
						netPlayScene.playerView1.shootState = true;
					}
					
					if(message.contains("hp")){
						if(message.length() < 10)
							hp2 = Integer.parseInt(message.substring(7,message.length()));
					}
					
					if(message.contains("scores")) {
						netPlayScene.win = false;
					}
					
					if(message.contains("end")) {
						end = true;
						stopClient();
					}
				 }
				});
				
			}catch(Exception e) {
				
				stopClient();
				break;
			}
		}
		
	}
	
	/**
	 * sends a message to the server
	 * @param message a message to server 
	 */
	public void send(String message) {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();
					byte[] buffer = message.getBytes();
					out.write(buffer);
					out.flush();
				}catch(Exception e) {
					stopClient();
				}
			}
		}; thread.start();
		
	}

	/**
	 * activates all necessary elements on the initial page at the start of the game
	 */
	public ViewManager() {
		stopClient();
		
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createButtons();
		createBackground();
		createLogo();

		//play music
		SoundManager.music(backsound);
		SoundManager.playSound(start);
		
		/**
		 * variables for the network
		 */
		first = 0;
		player1 = true;
		otherplayer = null;
		ready = true;
		stop = false;
		hp2 = 100;
		end = false;
		
		if(timer != null){
			timer.stop();
		}
		if(timeline1 != null) {
			timeline1.stop();
		}
	}

	/**
	 * 
	 * @return
	 */
	public Stage getMainStage() {
		return mainStage;
	}

	/**
	 * Coordinate and design setting for buttons
	 * @param button name of button
	 */
	private void addMenuButton(StarRaidersMainButton button) {
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 55);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}

	/**
	 * creates game menu buttons
	 */
	private void createButtons() {
		createPlayButton();
		createMATCHButton();
		createMatchButton();
		createOptionsButton();
		createHelpButton();
		createExitButton();
	}

	/**
	 * loads fxml file
	 * @param page name of file to load
	 */
	private void loadPage(String page) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));

			root.translateYProperty().set(mainPane.getHeight());
			mainPane.getChildren().add(root);

			Timeline timeline = new Timeline();
			KeyValue kv = new KeyValue(root.translateYProperty(), 80, Interpolator.EASE_IN);
			KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
			timeline.getKeyFrames().add(kf);
			timeline.play();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * play button action
	 */
	private void createPlayButton() {
		StarRaidersMainButton playButton = new StarRaidersMainButton("PLAY");
		addMenuButton(playButton);

		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SoundManager.playSound(Button_sound);
				SoundManager.music("src/model/resources/ghost.wav");
	
				playScene = new PlayScene(WIDTH, HEIGHT, mainStage, mainScene);
				mainStage.hide();
				mainStage.setScene(playScene.getScene());
				mainStage.show();
			}
		});
	}
	
	/**
	 * match button action
	 */
	private void createMATCHButton() {
		StarRaidersMainButton playButton = new StarRaidersMainButton("MATCH");
		addMenuButton(playButton);

		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SoundManager.playSound(Button_sound);
				SoundManager.music("src/model/resources/ghost.wav");
	
				playScene2 = new PlayScene2(WIDTH, HEIGHT, mainStage, mainScene);
				mainStage.hide();
				mainStage.setScene(playScene2.getScene());
				mainStage.show();
			}
		});
	}
	
	
	
	/**
	 * sends information about player1 and displays player2 information
	 */
	public void player2() {
		
		timeline1 = new Timeline(new KeyFrame(Duration.seconds(0.1), ev -> {
					
			if(netPlayScene.playerView.getScores() >= 400 ) {
				send("scores");
			}
			
			if(netPlayScene.hp == 90 && hpcon != 90) {
				send("hp90");
				hpcon = 90;
			}else if(netPlayScene.hp == 80 && hpcon != 80) {
				send("hp80");
				hpcon = 80;

			}else if(netPlayScene.hp == 70 && hpcon != 70) {
				send("hp70");
				hpcon = 70;

			}else if(netPlayScene.hp == 60 && hpcon != 60) {
				send("hp60");
				hpcon = 60;

			}else if(netPlayScene.hp == 50 && hpcon != 50) {
				send("hp50");
				hpcon = 50;

			}else if(netPlayScene.hp == 40 && hpcon != 40) {
				send("hp40");
				hpcon = 40;

			}else if(netPlayScene.hp == 30 && hpcon != 30) {
				send("hp30");
				hpcon = 30;

			}else if(netPlayScene.hp == 20 && hpcon != 20) {
				send("hp20");
				hpcon = 20;

			}else if(netPlayScene.hp == 10 && hpcon != 10) {
				send("hp10");
				hpcon = 10;

			}else if(netPlayScene.hp <= 0 && hpcon != 0) {
				send("hp0");
				hpcon = 0;
			}
			
			if(hp2 == 90) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp90.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
			}else if(hp2 == 80) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp80.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
			}else if(hp2 == 70) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp70.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
			}else if(hp2 == 60) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp60.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
			}else if(hp2 == 50) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp50.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
			}else if(hp2 == 40) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp40.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
			}else if(hp2 == 30) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp30.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
			}else if(hp2 == 20) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp20.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
			}else if(hp2 == 10) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp10.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
			}else if(hp2 == 0) {
				netPlayScene.pane.getChildren().remove(netPlayScene.hpimage);
				netPlayScene.hpimage = new ImageView(new Image("view/resources/hp0.png", 200,20,false, true));
				netPlayScene.hpimage.setLayoutX(580);
				netPlayScene.hpimage.setLayoutY(570);
				netPlayScene.pane.getChildren().add(netPlayScene.hpimage);
				timeline1.stop();
			}	
					
			
	 }));
		
		timeline1.setCycleCount(Animation.INDEFINITE);
		timeline1.play();	

	}
	
	
	/**
	 * send player1 information(received by keyboard)
	 */
	public void net() {
		
		netPlayScene.scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.W)) {
					send("pW");}
				else if(event.getCode().equals(KeyCode.A)) {
					send("pA");
				}else if(event.getCode().equals(KeyCode.S)) {
					send("pS");
				}else if(event.getCode().equals(KeyCode.D)) {
					send("pD");
				}else if(event.getCode().equals(KeyCode.J)) {
					double x = netPlayScene.playerView.player.getxIndex();
					double y = netPlayScene.playerView.player.getyIndex();
					
					send("pJ"+x+":"+y);
				}			
			}
		});
		
		
		netPlayScene.button.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			send("end");

		});
		
		
		netPlayScene.scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.W)) {
					send("rW");}
				else if(event.getCode().equals(KeyCode.A)) {
					send("rA");
				}else if(event.getCode().equals(KeyCode.S)) {
					send("rS");
				}else if(event.getCode().equals(KeyCode.D)) {
					send("rD");
				}else if(event.getCode().equals(KeyCode.J)) {
					send("rJ");
				}
			}
			
		});
	}
	

	/**
	 * match(N) button action 
	 * <p>
	 * This method starts the network 
	 */
	private void createMatchButton() {
		StarRaidersMainButton matchButton = new StarRaidersMainButton("MATCH(N)");
		addMenuButton(matchButton);
		
		matchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				SoundManager.playSound(Button_sound);
				
				try {
					startClient("127.0.01", 9876);
				}catch(Exception e) {
					e.printStackTrace();
				}

				loadPage("match");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				send("CONNECT");
				
				timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
					//stop before the other player is ready
					if(stop == true) {
				
						stop = false;
						first = 0;
						my = null;
						
						stopClient();
						timeline.stop();
					}
					
					//players are ready
					if(ready == false) {
						
						SoundManager.playSound(Button_sound);

						mainPane.getChildren().removeAll();
						SoundManager.music("src/model/resources/ghost.wav");

						netPlayScene = new netPlayScene(WIDTH, HEIGHT, mainStage, mainScene);
						mainStage.hide();
						mainStage.setScene(netPlayScene.getScene());
						mainStage.show();

						net();
						player2();		
						
						timeline.stop();		
					}					
					
			 }));
				
				timeline.setCycleCount(Animation.INDEFINITE);
				timeline.play();	
	
			}
		});
	}


	/**
	 * option button action
	 */
	private void createOptionsButton() {
		StarRaidersMainButton optionsButton = new StarRaidersMainButton("OPTIONS");
		addMenuButton(optionsButton);

		optionsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SoundManager.playSound(Button_sound);
				loadPage("OptionPage");
			}
		});
	}

	
	/**
	 * help button action
	 */
	private void createHelpButton() {
		StarRaidersMainButton helpButton = new StarRaidersMainButton("HELP");
		addMenuButton(helpButton);

		helpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SoundManager.playSound(Button_sound);
				loadPage("helpPage01");
			}
		});
	}

	/**
	 * exit button action
	 */
	private void createExitButton() {
		StarRaidersMainButton exitButton = new StarRaidersMainButton("EXIT");
		addMenuButton(exitButton);

		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SoundManager.playSound(Button_sound);
				stopClient();
				mainStage.close();
			}
		});
	}

	/**
	 * creates game background
	 */
	private void createBackground() {

		Image backgroundImage = new Image("model/resources/background1.png",WIDTH , HEIGHT, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}

	/**
	 * creates game logo
	 */
	private void createLogo() {

		logo = new Label();
		logo.setText("Zombie Crisis");
		logo.setFont(Font.font("Roboto Regular", 90));
		logo.setStyle("-fx-text-fill: ivory;  -fx-font-family:jokerman");
		logo.setLayoutY(85);

		TranslateTransition tl = new TranslateTransition(Duration.millis(1250), logo);
		tl.setFromX(750);
		tl.setToX(95);

		FadeTransition ft = new FadeTransition(Duration.millis(3500), logo); 
		ft.setFromValue(0.0);
		ft.setToValue(1.0); 

		ft.play();
		tl.play();

		mainPane.getChildren().add(logo);

		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				logo.setEffect(new DropShadow(45, Color.AQUA));

			}
		});
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				logo.setEffect(null);
			}
		});

	}
}