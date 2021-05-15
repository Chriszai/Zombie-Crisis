package view;

import java.util.ArrayList;

import application.Main;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.StarRaidersMainButton;
import model.Zombies;

/**
 * activates game components in network mode
 * @author Sungin Hwang
 * @version 
 */

public class netPlayScene {
	public Pane pane;
	public Scene scene;
	public Scene backScene;
	public Stage stage;

	public PlayerView2 playerView;
	public PlayerView3 playerView1;

	public StarRaidersMainButton button;

	public boolean win = true;
	public int zombieTime = 0;
	public int number = 0;
	public Timeline timeline;
	public Timeline timeline1;
	public Timeline timeline2;
	public int scores = 0;
	public int hp = 100;
	AnimationTimer timer;
	
	ArrayList<Zombies> zombies = new ArrayList<>();
	ArrayList<ZombiesView2> mon2 = new ArrayList<>();
	
	public ImageView hpimage;
	public Label mark;
	
	public static boolean DOWN = false;
	public static boolean UP = false;
	public static boolean RIGHT = false;
	public static boolean LEFT = false;

	/**
	 * 
	 * @param width
	 * @param height
	 * @param stage
	 * @param backScene
	 */
	public netPlayScene(int width, int height, Stage stage, Scene backScene) {
		timeline = new Timeline();
		timeline1 = new Timeline();
		timeline2 = new Timeline();
		pane = new Pane();
		scene = new Scene(pane, width, height);
		this.backScene = backScene;
		this.stage = stage;
		
		createBackground();
		createBackButton();
		createPlayer();
		createMark();
		
		createZombies();
		createZombies();
        createZombies();
        createZombies();
		
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				playerView.update();
				playerView.update1();
				playerView.update2();
 
				if (scores != playerView.getScores()) {
					pane.getChildren().remove(mark);
					createMark();
				}
				
				playerView1.update();
				playerView1.update1();
				playerView1.update2();
				
				if(Math.abs(playerView.player.getxIndex()-playerView1.player.getxIndex())< 0.6 && playerView1.player.getyIndex() - playerView.player.getyIndex() <0.8  && playerView1.player.getyIndex() > playerView.player.getyIndex()) {
					DOWN = true;
				}else {
					DOWN = false;
				}			
				if(Math.abs(playerView.player.getxIndex()-playerView1.player.getxIndex())< 0.6  && playerView.player.getyIndex() - playerView1.player.getyIndex() <0.8  && playerView1.player.getyIndex() < playerView.player.getyIndex()) {
					UP = true;
				}else {
					UP = false;
				}

				if(Math.abs(playerView.player.getyIndex()-playerView1.player.getyIndex())< 0.6  && playerView1.player.getxIndex() - playerView.player.getxIndex() <0.8  && playerView1.player.getxIndex() > playerView.player.getxIndex()) {
					RIGHT = true;
				}else {
					RIGHT = false;
				}
				
				if(Math.abs(playerView.player.getyIndex()-playerView1.player.getyIndex())< 0.6 && playerView.player.getxIndex() - playerView1.player.getxIndex() <0.8  && playerView1.player.getxIndex() < playerView.player.getxIndex()) {
					LEFT = true;
				}else {
					LEFT = false;
				}

				

				if (ViewManager.end == true) {
					pane.getChildren().remove(playerView);
					pane.getChildren().remove(playerView1);

					number = 0;
					timer.stop();

					timeline.stop();
					timeline1.stop();
					timeline2.stop();

					ViewManager.timeline1.stop();
					
					Main m = new Main();
					stage.close();
					stage.setScene(backScene);
					m.start(stage);

					Alert conn = new Alert(AlertType.ERROR);
					conn.setHeaderText("Server");
					conn.setContentText("The other player is disconnected");
					conn.show();
				}
			}
			
			
		};
		timer.start();

	}


	// background
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/playmap.png", scene.getWidth(), scene.getHeight(), false,
				true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		pane.setBackground(new Background(background));
	}

	private void createPlayer() {
		playerView = new PlayerView2(pane, zombies, mon2, scores, true);
		pane.getChildren().add(playerView);
		scene.setOnKeyPressed(playerView::handlePressed);
		scene.setOnKeyReleased(playerView::handleReleased);

		
		playerView1 = new PlayerView3(pane, zombies, mon2, scores, false);
		pane.getChildren().add(playerView1);

	}
	
	private void createZombies() {

		
		ZombiesView2 zombiesView = new ZombiesView2(playerView, playerView1);
		zombies.add(zombiesView.zombie);
		mon2.add(zombiesView);
		pane.getChildren().add(zombiesView);
		number++;


		
		// timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		// timeline1 = new Timeline();
		timeline1.setCycleCount(Timeline.INDEFINITE);

		timeline2.setCycleCount(Timeline.INDEFINITE);

		KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.02), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				zombiesView.closeMove(zombiesView.getHeight(), zombiesView.getWidth());
			}
		});

		KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (zombiesView.zombie.getHp() > 0) {
					
					// attack animation
					pane.getChildren().remove(zombiesView.atkEffect);
					zombiesView.attack(zombiesView.zombie);
					if (zombiesView.en == 1) {
						zombiesView.atkEffect = new ImageView(
								new Image("view/resources/ef1_1.png", 50, 50, true, true));
						zombiesView.atkEffect.setLayoutX(zombiesView.atkx);
						zombiesView.atkEffect.setLayoutY(zombiesView.atky);

						pane.getChildren().add(zombiesView.atkEffect);

					} else if (zombiesView.en == 2) {
						zombiesView.atkEffect = new ImageView(
								new Image("view/resources/ef1_2.png", 50, 50, true, true));
						zombiesView.atkEffect.setLayoutX(zombiesView.atkx);
						zombiesView.atkEffect.setLayoutY(zombiesView.atky);
						pane.getChildren().add(zombiesView.atkEffect);

					} else if (zombiesView.en == 3) {
						zombiesView.atkEffect = new ImageView(
								new Image("view/resources/ef1_3.png", 50, 50, true, true));
						zombiesView.atkEffect.setLayoutX(zombiesView.atkx);
						zombiesView.atkEffect.setLayoutY(zombiesView.atky);
						pane.getChildren().add(zombiesView.atkEffect);

					} else if (zombiesView.en == 4) {
						zombiesView.atkEffect = new ImageView(
								new Image("view/resources/ef1_4.png", 50, 50, true, true));
						zombiesView.atkEffect.setLayoutX(zombiesView.atkx);
						zombiesView.atkEffect.setLayoutY(zombiesView.atky);
						pane.getChildren().add(zombiesView.atkEffect);

					}
				}

				hpimage = new ImageView(new Image("view/resources/hp100.png", 200, 20, false, true));
				hpimage.setLayoutX(580);
				hpimage.setLayoutY(570);
				pane.getChildren().add(hpimage);

				if (zombiesView.player1.getHP() == 100) {
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp100.png", 304, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 90) {
					hp = 90;
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp90.png", 304, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 80) {
					hp = 80;
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp80.png", 304, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 70) {
					hp = 70;
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp70.png", 304, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 60) {
					hp = 60;
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp60.png", 304, 30, true, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 50) {
					hp = 50;
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp50.png", 304, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 40) {
					hp = 40;
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp40.png", 304, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 30) {
					hp = 30;
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp30.png", 304, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 20) {
					hp = 20;
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp20.png", 304, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else if (zombiesView.player1.getHP() == 10) {
					hp = 10;
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg = new ImageView(new Image("view/resources/hp10.png", 304, 30, false, true));
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				} else {
					hp = 0;
					playerView.hpimg = new ImageView(new Image("view/resources/hp0.png", 304, 30, false, true));
					pane.getChildren().remove(playerView.hpimg);
					playerView.hpimg.setLayoutX(0);
					playerView.hpimg.setLayoutY(570);
					pane.getChildren().add(playerView.hpimg);
				}
				// hp calculation
				if (zombiesView.player1.getHP() <= 0 || ViewManager.hp2 <= 0) {

					pane.getChildren().remove(playerView);
					pane.getChildren().remove(playerView1);

					timer.stop();
					timeline.stop();
					timeline1.stop();
					timeline2.stop();

					SoundManager.playSound("file:src/model/resources/gameover.wav");
					if(SoundManager.clip != null)
						SoundManager.clip.stop();

					if (ViewManager.hp2 <= 0) {
						
						GameSuccessViewManager newManager = new GameSuccessViewManager();
						stage.hide();
						stage = newManager.getMainStage();
						stage.show();

					} else {
						hp = 0;
						GameOverViewManager newManager = new GameOverViewManager();
						stage.hide();
						stage = newManager.getMainStage();
						stage.show();
					}
				}
				
			}
		});

		KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				
				// those codes is used for clear the zombie's keyframe
				if(win == false) {
					pane.getChildren().remove(playerView);
					pane.getChildren().remove(playerView1);

					timer.stop();
					timeline.stop();
					timeline1.stop();
					timeline2.stop();
					

					SoundManager.playSound("file:src/model/resources/gameover.wav");
					if(SoundManager.clip != null)
						SoundManager.clip.stop();
					if(ViewManager.timeline1 != null) 
						ViewManager.timeline1.stop();
					
					
					GameOverViewManager newManager = new GameOverViewManager();
					stage.hide();
					stage = newManager.getMainStage();
					stage.show();
				}
				
				if (zombiesView.zombie.getHp() <= 0) {
					timeline.getKeyFrames().remove(keyFrame1);
					timeline1.getKeyFrames().remove(keyFrame2);
					pane.getChildren().remove(zombiesView.atkEffect);
				}

        		if(playerView.getScores() < 350 && mon2.size()<6) {
        				createZombies();
        		}

				// victory condition
				if (playerView.getScores() >= 400) {
					pane.getChildren().remove(playerView);
					pane.getChildren().remove(playerView1);

					timer.stop();
					timeline.stop();
					timeline1.stop();
					timeline2.stop();

					SoundManager.playSound("file:src/model/resources/gameover.wav");
					if(SoundManager.clip != null)
						SoundManager.clip.stop();
					if(ViewManager.timeline1 != null) 
						ViewManager.timeline1.stop();
					
					GameSuccessViewManager newManager = new GameSuccessViewManager();
					stage.hide();
					stage = newManager.getMainStage();
					stage.show();

				}
			}
		});

		timeline.getKeyFrames().add(keyFrame1);
		timeline1.getKeyFrames().add(keyFrame2);
		timeline2.getKeyFrames().add(keyFrame3);

		timeline.play();
		timeline1.play();
		timeline2.play();

	}

	public Scene getScene() {
		return scene;
	}

	private void createBackButton() {
		button = new StarRaidersMainButton("BACK");
		button.setLayoutX(0);
		button.setLayoutY(0);
		pane.getChildren().add(button);

		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				number = 0;
				timer.stop();
				timeline.stop();
				timeline1.stop();
				timeline2.stop();

				if(ViewManager.timeline1 != null) 
					ViewManager.timeline1.stop();
				
				Main m = new Main();
				stage.close();
				stage.setScene(backScene);
				m.start(stage);
			}
		});
	}
	
	private void createMark() {

		mark = new Label();
		mark.setText("Scores:" + playerView.getScores());
		mark.setFont(Font.font("Roboto Regular", 20));
		mark.setStyle("-fx-text-fill: ivory;");
		mark.setLayoutX(680);

		pane.getChildren().add(mark);
	}
}
