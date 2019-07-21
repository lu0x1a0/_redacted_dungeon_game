package unsw.dungeon;

import java.util.ArrayList;


/**
 * Floorswitch entity which notifies observers it is being pressed
 * @author Brendan
 *
 */
public class FloorSwitch extends Entity implements Observable, Observer {

	private boolean isPressedDown = false;
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	
	/**
	 * Constructor for entity floorswitch
	 * @param x - int
	 * @param y - int
	 * @param dungeon - dungeon to be added to
	 */
	public FloorSwitch(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
	}
		
	/**
	 * returns boolean of whether the floor switch is being pressed
	 * @return - boolean
	 */
	public boolean isPressed() {
		return isPressedDown;
	}

	@Override
	public boolean isPassable() {
		return true;
	}

	/**
	 * notify the observers and note the switch is pressed
	 */
	protected void activatePressed() {
		isPressedDown = true;
		notifyObservers(this, true);
	}
	
	/**
	 * notify the observers and note the switch is now not pressed
	 */
	protected void deactivatePressed() {
		isPressedDown = false;
		notifyObservers(this, false);
	}
	
	@Override
	public void react(Entity obj) {
		if(obj instanceof Movable) {
			if(obj.getX() == this.getX() && obj.getY() == this.getY()) {
				((Movable) obj).registerObserver(this);
				activatePressed();
				return;
			}
		}
	}

	@Override
	public void update(Observable o, Object info) {
		
		if(o instanceof Movable) {
			if(((Entity) o).getX() != getX() || ((Entity) o).getY() != getY()){
				deactivatePressed();
				((Movable) o).removeObserver(this);
			}
		}
		
	}

	@Override
	public void notifyObservers(Observable e, Object info) {
		for(Observer o: observers) {
			o.update(e, info);
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
}
