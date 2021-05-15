package model;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class StarRaidersSubscreen extends SubScene{

	private final static String BACKGROUND_IMAGE = "model/resources/subscreen .png";
	private AnchorPane root2;
	private TranslateTransition transition;
		
	//SUBSCREEN DESIGN
	public StarRaidersSubscreen() {
		super(new AnchorPane(), 600, 500);
		prefWidth(600);
		prefHeight(500);
		
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 500, false,true),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
		
		root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(image));
		
		setLayoutX(100);
		setLayoutY(800);
		createCloseButton();
	}
	
	public void moveSubScene() {
		transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.4));
		transition.setNode(this);
		transition.setToY(-750);
		transition.play();		
	}

	public void backSubScene() {
		transition.setDuration(Duration.seconds(0.4));
		transition.setNode(this);
		transition.setToY(0);
		transition.play();
		
	}
	
	private void addCloseButton(StarRaidersCloseButton button) {
		button.setLayoutX(270);
		button.setLayoutY(450);
		root2.getChildren().add(button);
		
	}
	private void createCloseButton() {
		StarRaidersCloseButton closeButton = new StarRaidersCloseButton("close");
		addCloseButton(closeButton);
		
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				backSubScene();
			}
		});
	}
	

}
