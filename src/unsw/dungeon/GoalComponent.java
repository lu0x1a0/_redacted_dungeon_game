package unsw.dungeon;

public interface GoalComponent extends Observable, Observer{

	public boolean isComplete();
	public String printGoal(String message);
}