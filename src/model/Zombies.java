package model;


public class Zombies {
    private double xIndex;
    private double yIndex;
    public Player player;
    public int ID;
    public int atk;
    public int hp;
    public Zombies(double start_X, double start_Y,int ID) {
        this.xIndex = start_X;
        this.yIndex = start_Y;
        
        this.ID = ID;
        switch (ID) {
        case 1:
        	atk = 10;
        	hp = 30;//60
        	break;
        case 2:
        	atk = 20;
        	hp = 40;//80
        	break;
        case 3:
        	atk = 30;
        	hp = 50;//100
        	break;
        case 4:
        	atk = 40;
        	hp = 50;//100
        	break;
        	
        }
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
    public int getID() {
    	return ID;
    }
    public int getAtk() {
    	return atk;
    }
    public int getHp() {
    	return hp;
    }
    
    public void setHp(int hp) {
    	this.hp=hp;
    }
    

    
}
