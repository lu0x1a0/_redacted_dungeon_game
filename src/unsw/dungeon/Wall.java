package unsw.dungeon;

/**
 * static wall entity, fills up whole square and no other entities can go there
 * @author Brendan
 *
 */
public class Wall extends Entity {

	/**
	 * Constructor for wall, takes x,y coordinates and dungeon it is being added to
	 * @param x - int
	 * @param y - int
	 * @param dungeon - dungeon
	 */
    public Wall(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
    }

	@Override
	public boolean isPassable() {
		return false;
	}

	@Override
	public void react(Entity e) {		
	}

}
