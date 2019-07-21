package unsw.dungeon;

/**
 * Used in the composite pattern as the abstract component both subclasses implement
 * @author Brendan
 *
 */

public interface GoalComponent extends Observable, Observer{

	public boolean isComplete();
	public String printGoal(String message);
}
