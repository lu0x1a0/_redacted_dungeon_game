package unsw.dungeon;

import java.util.ArrayList;

public abstract class Movable extends Entity implements Observable {
    private ArrayList<Observer> observers;
    
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
	public abstract boolean checkPositionAvail();
	public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveLeft();
    public abstract void moveRight();
}
