package Test;

abstract public class Pieces {
	boolean good;
	boolean bed;	
	boolean live;
	
	public Pieces() {
		// TODO Auto-generated constructor stub
		live = true;
		bed = true;
		good = false;
	}
	
	public void setGood() {
		this.good = true;
		this.bed = false;
	}
	public void setBed() {
		this.good = false;
		this.bed = true;
	}
	
	public boolean getGood() {
		return good;
	}
	public boolean getBed() {
		return bed;
	}
}
