package unsw.dungeon;

/**
 * Treasure collectible, used for goals or points
 * @author Brendan
 *
 */
public class Treasure extends Collectible implements Observable {

	/**
	 * Constructor for Treasure collectible, takes x,y coordinates and dungeon it is being added to
	 * @param x - int
	 * @param y - int 
	 * @param dungeon - Dungeon
	 */
	public Treasure(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
	}
	
	@Override
	public boolean isPassable() {
		return true;
	}

	@Override
	public void react(Entity e) {
		if(e instanceof Player) {
			collect((Player) e);
			notifyObservers(this, true);
		}
		
	}
	@Override
	public void use(Object info) {
		return;
	}

}
