package unsw.dungeon;
/**
 * strategy interface for enemy's movement
 * @author Luoxi
 *
 */
public interface EnemyPath {
	/**
	 * 
	 * @param startx enemy's x coord
	 * @param starty enemy's y coord
	 * @return the coord to go to
	 */
	public Coord pathSearch(int startx, int starty);
}
