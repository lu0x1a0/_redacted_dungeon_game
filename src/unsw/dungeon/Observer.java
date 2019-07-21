package unsw.dungeon;

public interface Observer {
	/**
	 * The implementer of this interface will receive update info 
	 * and interpret the info
	 * @param o :observable
	 * @param info : info
	 */
	public void update(Observable o, Object info);
}
