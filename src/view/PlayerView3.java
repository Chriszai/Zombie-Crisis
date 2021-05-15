package view;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.PlayConfig;
import model.Player;
import model.Zombies;
import model.Bullet3;


public class PlayerView3 extends ImageView {
	
	public Player player;
	public int width;
	public int height;
	public ImageView hpimg;
	public Pane pane;
	public int scores;
	boolean up,down,left,right;
	public int state;
	boolean shootState = true;
	ArrayList<Bullet3> bullets = new ArrayList<>();
	ArrayList<Zombies> zombies;
	ArrayList<ZombiesView2> zombiesView;

	public int x = 0;

	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}

	public Direction direction;
	public PlayerView3(Pane pane,ArrayList<Zombies> zombies,ArrayList<ZombiesView2> zombiesView,int scores, boolean s) {
		
		this.zombiesView = zombiesView;
		this.zombies = zombies;
		this.pane = pane;
		
		if(s == true) {
			if(ViewManager.player1 == true) {
				player = new Player(3,3);
			}else {
				player = new Player(16,12);
			}
		}else {
			if(ViewManager.player1 == false) {
				player = new Player(3	,3);
			}else {
				player = new Player(16,12);
			}
		}
		
		width = PlayConfig.instance().getWidth() / PlayConfig.instance().getColumn();
		height = PlayConfig.instance().getHeight() / PlayConfig.instance().getRow();
		this.setImage(new Image("view/resources/player.png", width, height, false, true));
		this.setLayoutX(player.getxIndex() * width);
		this.setLayoutY(player.getyIndex() * height);
		this.setViewOrder(100.0);
		direction = Direction.UP;
		this.scores = scores;
		
		
		/*
		 * ImageView baseboard = new ImageView(); baseboard.setImage(new
		 * Image(BOARD_URI)); baseboard.setFitWidth(CommonConst.GAME_WIDTH);
		 * baseboard.setFitHeight(CommonConst.GAME_HEIGHT);
		 * baseboard.setLayoutX(CommonConst.GAME_X);
		 * baseboard.setLayoutY(CommonConst.GAME_Y); baseboard.setViewOrder(100.0);
		 * board.getChildren().add(baseboard);
		 */
	}
	



	public void handlePressed(KeyEvent keyEvent) {
		//System.out.println(bullets.size());
		
		if(keyEvent.getCode().equals(KeyCode.S)) {
			SoundManager.playSound("file:src/model/resources/foot.wav");
			down = true;
		}	
		if (keyEvent.getCode().equals(KeyCode.W)) {
			SoundManager.playSound("file:src/model/resources/foot.wav");
			up = true;
     	}	
		if (keyEvent.getCode().equals(KeyCode.A)) {
			SoundManager.playSound("file:src/model/resources/foot.wav");	
			left = true;
		}	
		if (keyEvent.getCode().equals(KeyCode.D)) {
			SoundManager.playSound("file:src/model/resources/foot.wav");
			right = true;
		}		

		if(keyEvent.getCode().equals(KeyCode.J) && shootState == true) {
			SoundManager.playSound("file:src/model/resources/shot.wav");
			shoot(player);
			shootState = false;
		}
	}
	
	public void handleReleased(KeyEvent keyEvent) {
		shootState = true;
		
		switch (keyEvent.getCode()) {
		case A:
			left=false;
			break;
		case D:
			right=false;
			break;
		case W:
			up=false;
			break;
		case S:
			down=false;
			break;
		case J:
			shootState = true;
		default:
			break;	
		}
	}

	public void turnRight() {

	}
	public void shoot(Player who) {
		
		Bullet3 bullet = new Bullet3(who,width,height,direction);
		pane.getChildren().add(bullet);
		bullets.add(bullet);
		
	}

	public Player getplayer() {
		return player;
	}
	
	public void update() {
		if (down) {
			boolean wall = false;
			if(player.getxIndex() > 9.24 && player.getxIndex() < 15.24) {
				wall = true;
			}		
			if (direction == Direction.DOWN && netPlayScene.UP == false) {
			    if (((wall == true && player.getyIndex() < 10.5) || (wall == false && player.getyIndex() < 12.9 ) )) {
				    player.setyIndex(player.getyIndex() + 0.05);
				    this.setLayoutY(player.getyIndex() * height);
				    this.setRotate(180);
			    }
			}
			else {
				this.setRotate(180);
				direction = Direction.DOWN;
			}
		} else if (up) {
			if (direction == Direction.UP && (netPlayScene.DOWN == false)) {
				if (player.getyIndex() > 0) {
					player.setyIndex(player.getyIndex() - 0.05);
					this.setLayoutY(player.getyIndex() * height);
				}
			}else {
				this.setRotate(0);
				direction = Direction.UP;
			}
		} else if (left) {
			boolean wall = false;
			if(player.getyIndex() > 10.6 && player.getxIndex() > 9.5) {
				wall = true;
			}				
			if (direction == Direction.LEFT && (netPlayScene.RIGHT == false) ) {
				if ((wall == true && player.getxIndex() > 15.3) || (wall == false && player.getxIndex() > 3.07)) {
					player.setxIndex(player.getxIndex() - 0.05);
					this.setLayoutX(player.getxIndex() * width);
				}
			} else {
				this.setRotate(270);
				direction = Direction.LEFT;
			}
		} else if (right) {
			boolean wall = false;
			if(player.getyIndex() > 10.6 && player.getxIndex() < 15.0) {
				wall = true;
			}		
			if (direction == Direction.RIGHT && netPlayScene.LEFT == false) {
				if ((wall == true && player.getxIndex() < 9.18) || (wall == false && player.getxIndex() < 16.3)) {
					player.setxIndex(player.getxIndex() + 0.05);
					this.setLayoutX(player.getxIndex() * width);
				}
			} else {
				direction = Direction.RIGHT;
				this.setRotate(90);
			}

		}
	}
	
	public void update1() {
		for(int i = bullets.size()-1;i>=0;i--) {
			if(bullets.get(i).getdirection() == 3) {
				bullets.get(i).moveRight();
			}
			else if(bullets.get(i).getdirection() == 2) {
				bullets.get(i).moveLeft();
			}
			else if(bullets.get(i).getdirection() == 0) {
				bullets.get(i).moveUp();
			}
			else {
				bullets.get(i).moveDown();
			}
			
			if(bullets.get(i).getx() > 17 || bullets.get(i).gety() > 13.5 || bullets.get(i).getx() < 2.9 || bullets.get(i).gety() * height < -20 
					|| (bullets.get(i).getx()>9.5 && bullets.get(i).getx() <15.17 && bullets.get(i).gety() > 10.9) 
					|| (bullets.get(i).gety()>10.9 && (bullets.get(i).getx() > 9.5) && bullets.get(i).getx()<15.17)) {
				pane.getChildren().remove(bullets.get(i));
				bullets.remove(bullets.get(i));
			}
			
		}
	}
	public void update2() {
		for(int j = zombies.size()-1;j >= 0;j--) {
			
		    for(int i = bullets.size()-1;i >= 0;i--) {
				if(zombies.size() <= j) {
					break;
				}
				double zbx = Double.parseDouble(String.format("%.3f", (zombies.get(j).getxIndex())));
				double zby = Double.parseDouble(String.format("%.3f", (zombies.get(j).getyIndex())));
				double blx = Double.parseDouble(String.format("%.3f", (bullets.get(i).getx())));
				double bly = Double.parseDouble(String.format("%.3f", (bullets.get(i).gety())));
					
				if (Double.parseDouble(String.format("%.3f", (Math.abs(bly - zby)))) < 0.5
						&& Double.parseDouble(String.format("%.3f", (Math.abs(blx - zbx)))) < 0.5) {						
					
					pane.getChildren().remove(bullets.get(i));
					bullets.remove(bullets.get(i));

					SoundManager.playSound("file:src/model/resources/hit.wav");
					zombies.get(j).setHp(zombies.get(j).getHp()-10);
						
					if(zombies.get(j).getHp() <= 0){
						
						SoundManager.playSound("file:src/model/resources/zombie.wav");
						pane.getChildren().remove(zombiesView.get(j));
		    		    this.scores += zombies.get(j).getID()*10;
		    		    zombies.remove(zombies.get(j));
		    		    zombiesView.remove(zombiesView.get(j));						
					}
				}
			}
		}
	}
	
	public int getScores() {
		return scores;
	}
}
