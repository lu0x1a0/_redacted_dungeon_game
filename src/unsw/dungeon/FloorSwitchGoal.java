package unsw.dungeon;

public class FloorSwitchGoal extends FloorSwitch {

	private boolean goalComplete;
	
	public FloorSwitchGoal(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
        goalComplete = false;
	}

	public boolean isGoalComplete() {
		return goalComplete;
	}

	public void setGoalComplete(boolean goalComplete) {
		this.goalComplete = goalComplete;
	}
	
	

}
