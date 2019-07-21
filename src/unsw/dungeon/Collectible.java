package unsw.dungeon;

import java.util.ArrayList;

public abstract class Collectible extends Entity implements Observable{
	protected boolean collected;
    private ArrayList<Observer> observers;
	public Collectible(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		observers = new ArrayList<Observer>();
		registerObserver(dungeon);
		collected = false;
	}
	public boolean isCollected() {
		return collected;
	}
	public abstract void use(Object info);
	
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
	@Override
	public boolean ispassable() {
		return true;
	}
	@Override
	public void react(Entity e) {
		if (e instanceof Player) {
			collect((Player) e);
		}
	}
	public void collect(Player player) {
		player.addToInventory(this);
		this.removeFromView();
		collected  = true;
		this.postCollect();
	}
	public abstract void postCollect();
}
