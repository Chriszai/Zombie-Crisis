package model;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

public class StarRaidersCloseButton extends Button{
	

	private final String BUTTON_STYLE = ("-fx-background-color: grey;-fx-text-fill:white;");
	private final String BUTTON_HOVER_STYLE = ("-fx-background-color: black;-fx-text-fill:white;");
	
	//BUTTON_STYLE
	public  StarRaidersCloseButton(String text) {
		
		setText(text);
		setButtonFont();
		setPrefHeight(20);
		setPrefWidth(60);
		setStyle(BUTTON_STYLE);
		initializeButtonListeners();
		
		}
	
	private void setButtonFont() {
		setFont(Font.font("family",10));
	}
	
	private void setButtonReleasedStyle() {
		setStyle(BUTTON_STYLE);
	}
	
	private void setButtonPressedStyle() {
		setStyle(BUTTON_HOVER_STYLE);

	}
	
	private void initializeButtonListeners() {
		
		setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				setButtonPressedStyle();
			}
		});
		
		
		setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setButtonReleasedStyle();
			}
		});
	}
	
}
