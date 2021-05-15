package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;


public class HelpPageController implements Initializable{
	private static final String click = "file:src/model/resources/click.wav";
	
	@FXML
	private AnchorPane container01;
	@FXML
	private Button closeBtn;
	@FXML
	private Button prevBtn;
	@FXML
	private Label help;
	@FXML
	private Button nextBtn;
	@FXML
	private ImageView keyboard;
	@FXML
	private ImageView shot;
	@FXML
	private void closeClick() {
		container01.setTranslateY(-500);
		ViewManager.logo.setEffect(new DropShadow(65, Color.AQUA));
		SoundManager.playSound(click);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		help.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				help.setEffect(new DropShadow(15, Color.DARKGREEN));
			}
			
		});
		help.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				help.setEffect(null);
			}
			
		});
		
		if(container01 != null) {
			Image img = new Image("file:src/view/resources/keyboard.png",150,100,false,true);
			keyboard.setImage(img);
			
			Image img2 = new Image("file:src/view/resources/J.png",70,70,false,true);
			shot.setImage(img2);
		}
	}

}
