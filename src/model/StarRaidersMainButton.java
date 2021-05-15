package model;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import view.SoundManager;

public class StarRaidersMainButton extends Button{
	
	//BUTTON DESIGN
	private final String BUTTON = ("-fx-background-color: black;-fx-text-fill:white; -fx-font-family:Ravie");
	private final String BUTTON_HOVER = ("-fx-border-color:transparent,black; -fx-background-color: white;-fx-text-fill:black;");

	private static final String Button_sound = "file:src/model/resources/mainbutton_sound.wav";
	
	public StarRaidersMainButton(String text) {
		
		setText(text);
		setButtonFont();
		setPrefHeight(20);
		setPrefWidth(150);
		setStyle(BUTTON);
		initializeButtonListeners();

		}
	
	private void setButtonFont() {
		setFont(Font.font("family",18));
	}
	
	private void setButtonReleasedStyle() {
		setStyle(BUTTON);
	}
	
	private void setButtonPressedStyle() {
		setStyle(BUTTON_HOVER);

		
	}

	
	private void initializeButtonListeners() {
		
		setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				
				setButtonPressedStyle();
			    
			    //effect sound 
				SoundManager.playSound(Button_sound);
				
			}
		});
		
		
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setButtonReleasedStyle();

				//effect sound
				SoundManager.playSound(Button_sound);
				
			}
		});
				
	}
	
}
