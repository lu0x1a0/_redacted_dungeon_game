package unsw.dungeon;

/**
 * Used in the composite pattern as the abstract component both subclasses implement
 * @author Brendan
 *
 */

public interface GoalComponent extends Observable, Observer{
	/**
	 * returns whether or not the goal is complete
	 * @return - Boolean
	 */
	public boolean isComplete();
	/**
	 * returns a message of the goals in the level
	 * @param message - takes previous goals up to this point
	 * @return - returns previous goals plus its own appended
	 */
	public String printGoal(String message);
}
