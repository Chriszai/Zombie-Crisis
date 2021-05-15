package model;


public class Player {
    private double xIndex;
    private double yIndex;
    public int maxHP =100;//the max numbet of hp
    public int HP=100;//the hp of the actor now
    public Player(int start_X, int start_Y) {
        this.xIndex = start_X;
        this.yIndex = start_Y;
    }
    public double getxIndex() {
        return xIndex;
    }
    public void setxIndex(double xIndex) {
        this.xIndex = xIndex;
    }
    public double getyIndex() {
        return yIndex;
    }
    public void setyIndex(double yIndex) {
        this.yIndex = yIndex;
    }
    public int getHP() {
    	return HP;
    }
    
    public void setHP(int hp) {
    	this.HP=hp;
    }
}
