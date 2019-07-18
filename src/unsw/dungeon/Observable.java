package unsw.dungeon;

import java.util.ArrayList;

public interface Observable {
	public ArrayList<Observer> observers = new ArrayList<Observer>();
	public default void notifyObservers(Observable e, Object info) {
		for(Observer o: observers) {
			o.update(e, info);
		}
	}
	public default void removeObserver(Observer o) {
		observers.remove(o);
	}
	public default void registerObserver(Observer o) {
		observers.add(o);
	}
}
