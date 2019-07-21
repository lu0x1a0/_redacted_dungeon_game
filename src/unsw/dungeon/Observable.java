package unsw.dungeon;


public interface Observable {
	/**
	 * notifyObserver about change
	 * @param e: the observable
	 * @param info: updated data
	 */
	public void notifyObservers(Observable e, Object info);
	/**
	 * remove Observer from observable
	 * @param o: observer that need not to listen for change in future
	 */
	public void removeObserver(Observer o);
	/**
	 * attach Observer to observable
	 * @param o: observer
	 */
	public void registerObserver(Observer o);
}
