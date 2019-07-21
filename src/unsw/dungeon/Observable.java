package unsw.dungeon;

import java.util.ArrayList;

public interface Observable {
	public void notifyObservers(Observable e, Object info);
	public void removeObserver(Observer o);
	public void registerObserver(Observer o);
}
