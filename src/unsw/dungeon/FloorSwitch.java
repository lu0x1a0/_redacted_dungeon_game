package unsw.dungeon;

import java.util.ArrayList;

public class FloorSwitch extends Entity implements Observable, Observer {

	private boolean isPressedDown = false;
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	
	
	public FloorSwitch(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
		// TODO Auto-generated constructor stub
	}
		
	public boolean isPressed() {
		return isPressedDown;
	}

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return true;
	}

	protected void activatePressed() {
		isPressedDown = true;
		notifyObservers(this, true);
	}

	protected void deactivatePressed() {
		isPressedDown = false;
		notifyObservers(this, false);
	}

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
