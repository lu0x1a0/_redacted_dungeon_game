package unsw.dungeon;

import java.util.ArrayList;
/**
 * common properties abstracted away for all entities that can move in the map
 * @author Sean
 *
 */
public abstract class Movable extends Entity implements Observable {
    private ArrayList<Observer> observers;
    /**
     * Create Movable
     * @param x: coord x
     * @param y: coord y
     * @param dungeon: dungeon object
     */
    public Movable(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		observers = new ArrayList<Observer>();
		registerObserver(dungeon);
	}
    @Override
	public void notifyObservers(Observable e, Object info) {
		for(int i = 0; i<observers.size();i++) {
    		observers.get(i).update(e, info);
		}
	}
	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}

	
	/**
	 * move object on map up
	 */
	public abstract void moveUp();
	/**
	 * move object on map down
	 */
    public abstract void moveDown();
    /**
	 * move object on map left
	 */
    public abstract void moveLeft();
    /**
	 * move object on map right
	 */
    public abstract void moveRight();
}
