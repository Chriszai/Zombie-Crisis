package view;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ZombiesConfig;
import model.Zombies;
import model.Player;



public class ZombiesView extends ImageView {
	public Zombies zombie;
	private int width;
	private int height;
	public PlayerView pv;
	public int bodyStyle = 0;
	double diffx;
	double diffy;
	public int count;
	public int en = 0;
	public double speedList[] = { 0.04, 0.04 };
	public int zombiesID[] = { 1, 2, 3, 1, 4, 2, 1, 2, 1, 3, 1 };
	public int[][] location = { { 6, 7 }, { 12, 5 }, { 14, 8 } };
	int len1;
	int len2;
	double speed;
	public ImageView atkEffect;
	public double atkx;
	public double atky;
	public double distance;// the distance between zombie and palyer
	Player player;

	private enum Direction {
		UP, DOWN, LEFT, RIGHT, UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT;
	}

	private Direction direction;

	public ZombiesView(PlayerView pv) {
		this.pv = pv;
		player = pv.getplayer();
		// Random random = new Random();
		int[] position = randomLocation();
		zombie = new Zombies(position[0], position[1], randomID());
		width = ZombiesConfig.instance().getWidth() / ZombiesConfig.instance().getColumn();
		height = ZombiesConfig.instance().getHeight() / ZombiesConfig.instance().getRow();
		if (zombie.getID() == 1) {
			this.setImage(new Image("view/resources/A0.png", width, height, false, true));
		} else if (zombie.getID() == 2) {
			this.setImage(new Image("view/resources/B0.png", width, height, false, true));
		} else if (zombie.getID() == 3) {
			this.setImage(new Image("view/resources/C0.png", width, height, false, true));
		} else {
			this.setImage(new Image("view/resources/D0.png", width, height, false, true));
		}

		this.setLayoutX(zombie.getxIndex() * width);
		this.setLayoutY(zombie.getyIndex() * height);
		this.setViewOrder(100.0);
		direction = Direction.UP;
		speed = randomSpeed();
		count = 0;

		/*
		 * ImageView baseboard = new ImageView(); baseboard.setImage(new
		 * Image(BOARD_URI)); baseboard.setFitWidth(CommonConst.GAME_WIDTH);
		 * baseboard.setFitHeight(CommonConst.GAME_HEIGHT);
		 * baseboard.setLayoutX(CommonConst.GAME_X);
		 * baseboard.setLayoutY(CommonConst.GAME_Y); baseboard.setViewOrder(100.0);
		 * board.getChildren().add(baseboard);
		 */
	}

	public void closeMove1(int height, int width) {

		diffy = Double.parseDouble(String.format("%.3f", (zombie.getyIndex() - player.getyIndex())));
		diffx = Double.parseDouble(String.format("%.3f", (zombie.getxIndex() - player.getxIndex())));
		distance = Math.sqrt(Math.abs(diffx) * Math.abs(diffx) + Math.abs(diffy) * Math.abs(diffy));

		// System.out.println(zombies.getyIndex() - player.getyIndex());
		if (distance > 1) {
			if (count >= 20) {
				changeBodyStyle();
				count = 0;
			}
			if (Math.abs(diffx) > Math.abs(diffy)) {
				if (diffx > 0) {
					zombie.setxIndex(zombie.getxIndex() - (speed));
					this.setLayoutX(zombie.getxIndex() * width);
					direction = Direction.LEFT;
					count++;
					this.setRotate(270);

				} else if (diffx < 0) {
					zombie.setxIndex(zombie.getxIndex() + (speed));
					this.setLayoutX(zombie.getxIndex() * width);
					direction = Direction.RIGHT;
					count++;
					this.setRotate(90);
				}
			} else {
				if (diffy > 0) {
					zombie.setyIndex(zombie.getyIndex() - (speed));
					this.setLayoutY(zombie.getyIndex() * height);
					direction = Direction.UP;
					count++;
					this.setRotate(0);
				} else if (diffy < 0) {
					zombie.setyIndex(zombie.getyIndex() + (speed));
					this.setLayoutY(zombie.getyIndex() * height);
					direction = Direction.DOWN;
					count++;
					this.setRotate(180);
				}
			}

			setImage(zombie.getID());
		}
	}

	public void closeMove(int height, int width) {

		diffy = Double.parseDouble(String.format("%.3f", (zombie.getyIndex() - player.getyIndex())));
		diffx = Double.parseDouble(String.format("%.3f", (zombie.getxIndex() - player.getxIndex())));
		distance = Math.sqrt(Math.abs(diffx) * Math.abs(diffx) + Math.abs(diffy) * Math.abs(diffy));

		if (distance > 1) {
			if (count >= 20) {
				changeBodyStyle();
				count = 0;
			}

			if ((player.getxIndex() < zombie.getxIndex()) && (diffy == 0)) {
				zombie.setxIndex(zombie.getxIndex() - speed);
				this.setLayoutX(zombie.getxIndex() * width);
				direction = Direction.LEFT;
				count++;
				this.setRotate(270);

			} else if ((player.getxIndex() > zombie.getxIndex()) && (diffy == 0)) {
				zombie.setxIndex(zombie.getxIndex() + speed);
				this.setLayoutX(zombie.getxIndex() * width);
				direction = Direction.RIGHT;
				count++;
				this.setRotate(90);

			} else if ((player.getyIndex() < zombie.getyIndex()) && (diffx == 0)) {
				zombie.setyIndex(zombie.getyIndex() - speed);
				this.setLayoutY(zombie.getyIndex() * height);
				direction = Direction.UP;
				count++;
				this.setRotate(0);

			} else if ((player.getyIndex() > zombie.getyIndex()) && (diffx == 0)) {
				zombie.setyIndex(zombie.getyIndex() + speed);
				this.setLayoutY(zombie.getyIndex() * height);
				direction = Direction.DOWN;
				count++;
				this.setRotate(180);

			} else if ((player.getxIndex() < zombie.getxIndex()) && (player.getyIndex() < zombie.getyIndex())) {
				zombie.setxIndex(zombie.getxIndex() - speed);
				this.setLayoutX(zombie.getxIndex() * width);
				zombie.setyIndex(zombie.getyIndex() - speed);
				this.setLayoutY(zombie.getyIndex() * height);
				direction = Direction.UPLEFT;
				count++;
				this.setRotate(315);
			} else if ((player.getxIndex() < zombie.getxIndex()) && (player.getyIndex() > zombie.getyIndex())) {
				zombie.setxIndex(zombie.getxIndex() - speed);
				this.setLayoutX(zombie.getxIndex() * width);
				zombie.setyIndex(zombie.getyIndex() + speed);
				this.setLayoutY(zombie.getyIndex() * height);
				direction = Direction.DOWNLEFT;
				this.setRotate(225);
				count++;
			} else if ((player.getxIndex() > zombie.getxIndex()) && (player.getyIndex() < zombie.getyIndex())) {
				zombie.setxIndex(zombie.getxIndex() + speed);
				this.setLayoutX(zombie.getxIndex() * width);
				zombie.setyIndex(zombie.getyIndex() - speed);
				this.setLayoutY(zombie.getyIndex() * height);
				direction = Direction.UPRIGHT;
				this.setRotate(45);
				count++;
			} else if ((player.getxIndex() > zombie.getxIndex()) && (player.getyIndex() > zombie.getyIndex())) {
				zombie.setxIndex(zombie.getxIndex() + speed);
				this.setLayoutX(zombie.getxIndex() * width);
				zombie.setyIndex(zombie.getyIndex() + speed);
				this.setLayoutY(zombie.getyIndex() * height);
				direction = Direction.DOWNRIGHT;
				this.setRotate(115);
				count++;
			}
			setImage(zombie.getID());
		}
	}

	public void setImage(int ID) {
		String n;
		if (ID == 1) {
			n = "A";
		} else if (ID == 2) {
			n = "B";
		} else if (ID == 3) {
			n = "C";
		} else {
			n = "D";
		}
		if (bodyStyle == 0) {
			this.setImage(new Image("view/resources/" + n + "0.png", width, height, false, true));
		} else if (bodyStyle == 1) {
			this.setImage(new Image("view/resources/" + n + "1.png", width, height, false, true));
		} else if (bodyStyle == 2) {
			this.setImage(new Image("view/resources/" + n + "0.png", width, height, false, true));
		} else {
			this.setImage(new Image("view/resources/" + n + "2.png", width, height, false, true));
		}
	}

	public void attack(Zombies zombie) {
		double range = Math.sqrt(Math.abs(diffx) * Math.abs(diffx) + Math.abs(diffy) * Math.abs(diffy));

		if (range <= 1 && en == 0) {
			en = 1;
			if (direction == Direction.UP) {
				atkx = width * zombie.getxIndex() - 5;
				atky = height * zombie.getyIndex() - 20;
			} else if (direction == Direction.DOWN) {
				atkx = width * zombie.getxIndex() - 5;
				atky = height * zombie.getyIndex() + 20;
			} else if (direction == Direction.UPLEFT) {
				atkx = width * zombie.getxIndex() - 20;
				atky = height * zombie.getyIndex() - 20;
			} else if (direction == Direction.UPRIGHT) {
				atkx = width * zombie.getxIndex() + 5;
				atky = height * zombie.getyIndex() - 20;
			} else if (direction == Direction.LEFT) {
				atkx = width * zombie.getxIndex() - 20;
				atky = height * zombie.getyIndex() - 10;

			} else if (direction == Direction.RIGHT) {
				atkx = width * zombie.getxIndex() + 20;
				atky = height * zombie.getyIndex() - 5;
			} else if (direction == Direction.DOWNLEFT) {
				atkx = width * zombie.getxIndex() - 20;
				atky = height * zombie.getyIndex() + 10;
			} else if (direction == Direction.DOWNRIGHT) {
				atkx = width * zombie.getxIndex() + 10;
				atky = height * zombie.getyIndex() + 10;
			}
		} else if (en == 1) {
			en = 2;

		} else if (en == 2) {
			en = 3;

		} else if (en == 3) {
			en = 4;
			player.setHP(player.getHP() - zombie.getAtk());
		} else if (en == 4) {
			en = 0;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double randomSpeed() {
		len1 = speedList.length;
		Random random = new Random();
		int arrIdx = random.nextInt(len1 - 1);
		double speed = speedList[arrIdx];
		return speed;
	}

	public int randomID() {
		len2 = zombiesID.length;
		Random random = new Random();
		int arrIdx = random.nextInt(len2 - 1);
		int ID = zombiesID[arrIdx];
		return ID;
	}

	public int[] randomLocation() {
		Random random = new Random();
		int arrIdx = random.nextInt(3);
		int[] ID = location[arrIdx];
		return ID;
	}

	public void changeBodyStyle() {

		if (bodyStyle == 0) {
			bodyStyle = 1;
		} else if (bodyStyle == 1) {
			bodyStyle = 2;
		} else if (bodyStyle == 2) {
			bodyStyle = 3;
		} else {
			bodyStyle = 0;
		}
	}

}
