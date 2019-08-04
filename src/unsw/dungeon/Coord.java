package unsw.dungeon;


/**
 * class used a key for a location on the map
 * @author compu
 *
 */
public class Coord {
	private int x, y;
	/**
	 * this is a class used as a key for objects location in the map
	 * @param x - int
	 * @param y - int
	 */
	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * getter for X coordinate
	 * @return int
	 */
	public int getX() {
		return x;
	}
	/**
	 * setter for X coordinate
	 * @param - int
	 * @return
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * getter for Y coordinate
	 * @return int
	 */
	public int getY() {
		return y;
	}
	/**
	 * setter for Y coordinate
	 * @param - int
	 * @return
	 */
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coord other = (Coord) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Coord [x=" + x + ", y=" + y + "]";
	}
	
}
