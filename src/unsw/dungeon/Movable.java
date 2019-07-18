package unsw.dungeon;

public interface Movable extends Observable {
    public boolean checkPositionAvail();
	public void moveUp();
    public void moveDown();
    public void moveLeft();
    public void moveRight();
}
