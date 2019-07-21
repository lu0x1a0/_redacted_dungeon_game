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
		//for(Observer o: observers) {
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
	 * move in specified direction in the map
	 */
	public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();
}
