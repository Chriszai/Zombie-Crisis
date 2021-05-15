package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * handles waiting room UI and username creation UI
 * @author Sungin Hwang
 *
 */
public class matchController implements Initializable {

	@FXML
	public TextField Usernamefield;
	@FXML
	public Button createBtn;
	@FXML
	private AnchorPane matchpane;
	
	@FXML
	private Label labp1;
	@FXML
	private Label labp2;
	@FXML
	private AnchorPane waitingpane;
	@FXML
	private Label lab1;
	@FXML
	private Label lab2;
	@FXML
	private Label lab3;
	@FXML
	private Label player1;
	@FXML
	private Label player2;
	@FXML
	private Button backBtn;
	
	public static String username;
	public Timeline timeline;
	
	private int i = 1;
		
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		if(createBtn != null) {
			
			SoundManager.music("src/model/resources/type.wav");
			createBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				/**
				 * notify users when the server is closed
				 */
				if(ViewManager.socket == null) {
					Alert conn = new Alert(AlertType.ERROR);
					conn.setHeaderText("Server");
					conn.setContentText("Check your connection and try again");
					conn.showAndWait();
					
					SoundManager.music(ViewManager.backsound);
					matchpane.setLayoutY(-500);
										
				}else {

				if(Usernamefield.getText().equals("")) {
					
					Alert checkid = new Alert(AlertType.ERROR);
					checkid.setHeaderText("EMPTY");
					checkid.setContentText("Empty area");
					checkid.showAndWait();		
					
				}else {
					
					username = Usernamefield.getText();
					
					try {
						Parent root = FXMLLoader.load(getClass().getResource("waitingroom.fxml"));
                        ViewManager.mainPane.getChildren().add(root);	
                       
						Thread.sleep(100);
                        matchpane.setTranslateY(-600);
                        SoundManager.music(ViewManager.backsound);
					
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				 }
				}
				
			}
			
		});
		
		DropShadow shadow = new DropShadow();
		
		createBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				createBtn.setEffect(shadow);
			}
			
		});
		
		createBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				createBtn.setEffect(null);
			}		
		});
		
		}
	

		/**
		 * 
		 */
		if(backBtn != null) {
			
			DropShadow shadow = new DropShadow();
			backBtn.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					backBtn.setEffect(shadow);
				}
				
			});
			
			backBtn.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					backBtn.setEffect(null);
				}
				
			});
			
			backBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					
					if(ViewManager.player != null) {
						
						Alert conn = new Alert(AlertType.ERROR);
						conn.setHeaderText("Alarm");
						conn.setContentText("The other player is connecting now");
						conn.showAndWait();					
						}else {
							
						ViewManager.stop = true;
						waitingpane.setTranslateY(-600);
					}
				}
				
			});
		}
		
		/**
		 * 
		 */
		if(waitingpane != null) {
			
			if(ViewManager.player1 == true) {
				
				labp1.setText(username);	
				labp1.setStyle("-fx-text-fill:#FF000080;");
				player1.setStyle("-fx-text-fill:#FF6A5ACD");
				
			}else {
				
				labp2.setText(username);
				player2.setStyle("-fx-text-fill:#FF6A5ACD");
				labp2.setStyle("-fx-text-fill:#FF000080;");
			}
		

			timeline = new Timeline(new KeyFrame(Duration.seconds(0.01), ev -> {

				if(ViewManager.otherplayer != null) {
					
					if(ViewManager.player1 == true) {
						labp2.setText(ViewManager.otherplayer);
					}else {
						labp1.setText(ViewManager.otherplayer);	
					}
					
						if(i == 300) {
							SoundManager.playSound("file:src/model/resources/count.wav");

							lab1.setText(" 3");
							lab1.setStyle("-fx-text-fill:red; -fx-background-color:black");
						
						}if(i == 400) {
							lab1.setText("");
							lab1.setStyle(null);
							
							lab2.setStyle("-fx-text-fill:red; -fx-background-color:black");
							lab2.setText(" 2");

						}if(i == 500) {
							lab2.setText("");
							lab2.setStyle(null);
							
							lab3.setText(" 1");
							lab3.setStyle("-fx-text-fill:red; -fx-background-color:black");
						}if(i == 600) {		
							timeline.stop();							
							ViewManager.ready = false;
							waitingpane.setTranslateY(-600);
							
							username = null;
						}
						i++;
					}
			}));
			
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();

		}
		
	}


}
