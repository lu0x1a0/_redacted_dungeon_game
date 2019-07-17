package unsw.dungeon;

/**
 * The player entity
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity implements Movable{

    private Dungeon dungeon;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y);
        this.dungeon = dungeon;
    }
    @Override
    public void moveUp() {
        if (getY() > 0 && 
			dungeon.ispassable(getX(), getY() - 1) == true)
            y().set(getY() - 1);
    }
    @Override
    public void moveDown() {
        if (getY() < dungeon.getHeight() - 1 && 
			dungeon.ispassable(getX(), getY() + 1) == true)
            y().set(getY() + 1);
    }
    @Override
    public void moveLeft() {
    	if (getX() > 0 && 
			dungeon.ispassable(getX() - 1, getY()) == true)
            x().set(getX() - 1);
    }
    @Override
    public void moveRight() {
        if (getX() < dungeon.getWidth() - 1 && 
			dungeon.ispassable(getX() + 1, getY()) == true)
            x().set(getX() + 1);
    }
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void registerObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean checkPositionAvail() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return true;
	}

}
