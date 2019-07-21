package unsw.dungeon;

import java.util.ArrayList;

/**
 * common properties abstracted away for all entities that can be collected in the map
 * @author Sean
 *
 */
public abstract class Collectible extends Entity implements Observable{
	protected boolean collected;
    private ArrayList<Observer> observers;
    /**
     * Constructor for abstract class
     * @param x - x coordinate
     * @param y - y coordinate
     * @param dungeon - dungeon to add it to
     */
	public Collectible(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		observers = new ArrayList<Observer>();
		registerObserver(dungeon);
		collected = false;
	}
	
	/**
	 * returns whether or not the item is collected by player
	 * @return boolean
	 */
	public boolean isCollected() {
		return collected;
	}
	
	/**
	 * Action of the object, defined in object type itself
	 * @param info - additional info in notification
	 */
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
	public boolean isPassable() {
		return true;
	}
	@Override
	public void react(Entity e) {
		if (e instanceof Player) {
			collect((Player) e);
		}
	}
	/**
	 * give this collectible to the player's inventory and remove from game view
	 * @param player - player
	 */
	public void collect(Player player) {
		player.addToInventory(this);
		this.removeFromView();
		collected  = true;
		this.postCollect();
	}
	/**
	 * what to do after the collect stage
	 * i.e. is there any effect
	 */
	public void postCollect() {
		return;
	};
}
