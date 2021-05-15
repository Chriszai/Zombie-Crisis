package view;

import java.util.ArrayList;
import java.util.List;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.StarRaidersMainButton;

public class GameSuccessViewManager {
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	
	private AnchorPane mainPane;
	public Scene mainScene;
	public Stage mainStage;
	
	private final static int MENU_BUTTONS_START_X = 340;
	private final static int MENU_BUTTONS_START_Y = 400;
		
	List<StarRaidersMainButton> menuButtons;
	
	public GameSuccessViewManager() {
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane,WIDTH,HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);

		createButtons();
		createBackground();
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	public Scene getMainScene() {
        return mainScene;
    }
	
	private void addMenuButton(StarRaidersMainButton button) {

		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 50);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	
	}
	
	//Create buttons
	private void createButtons() {
		createRestartButton();
		createExitButton();
	}
	
	//Restart Button
	private void createRestartButton() {
		StarRaidersMainButton restartButton = new StarRaidersMainButton("Restart");
		addMenuButton(restartButton);
		
		restartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				try {
					if(ViewManager.socket != null && !ViewManager.socket.isClosed())
						ViewManager.socket.close();
				}catch( Exception e) {
					e.printStackTrace();
				}
				
				Main m = new Main();
				mainStage.close();
				m.start(mainStage);				
			}
		});
	}
	
	//Exit Button
	private void createExitButton() {
		StarRaidersMainButton exitButton = new StarRaidersMainButton("EXIT");
		addMenuButton(exitButton);
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainStage.close();
			}			
		});
	}
	
	//Background
	private void createBackground()	{

		Image backgroundImage = new Image("view/resources/game_success_background.png", 800, 600, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,null);
		mainPane.setBackground(new Background(background));
		
	}	
}
