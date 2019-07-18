package unsw.dungeon;

public interface Goal extends Observable{
	public boolean canComplete();
	public boolean iscomplete();
	public void setComplete();
}
