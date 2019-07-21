package unsw.dungeon;


/**
 * Sword class, used to kill enemy and only has 5 uses
 * @author Brendan
 *
 */
public class Sword extends Collectible {
	private int count;
	
	/**
	 * Constructor for sword, takes in x, y coordinate (int) and then the dungeon it is being added to
	 * @param x - int
	 * @param y - int
	 * @param dungeon - dungeon
	 */
	public Sword(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
        count = 5;
	}

	/**
	 * returns uses left of sword
	 * @return - int
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Set number of uses left
	 * @param count - int
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * method for when the sword is broken after 5 uses
	 * @param p - player with the now broken sword
	 */
	public void swordBroken(Player p) {
		p.removeSword();
	}
	@Override
	public void use(Object info) {
		if(info instanceof Player) {
			Player p = (Player) info;
			notifyObservers(this, new Coord(p.getX(),p.getY()));
		}
	}
	@Override
	public void react(Entity e) {
		if (e instanceof Player) {
			Player p = (Player) e;
			if (!p.hasSword()) {
				collect((Player) e);
			}
		}
	}

}
