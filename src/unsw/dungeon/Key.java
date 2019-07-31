package unsw.dungeon;

/**
 * Key which links to a door with the same ID to unlock
 * @author Brendan
 *
 */
public class Key extends Collectible {
	private int id;
	/**
	 * Constructor of key that takes in location, dungeon and the id of matching door
	 * @param x - int
	 * @param y - int
	 * @param dungeon - Dungeon
	 * @param id - int
	 */
	public Key(int x, int y, Dungeon dungeon,int id) {
        super(x, y, dungeon);
        this.id = id;
	}

	/**
	 * returns the assocaited ID of key
	 * @return - int
	 */
	public int getId() {
		return id;
	}

	@Override
	public void use(Object info) {
		if(info instanceof Player) {
			((Player) info).removeKey();
		}
	}
	@Override
	public void react(Entity e) {
		if (e instanceof Player) {
			Player p = (Player) e;
			if (p.getKey() == null) {
				collect((Player) e);
			}
		}
	}
	@Override
	public boolean isPassable() {
		return true;
	}

}
