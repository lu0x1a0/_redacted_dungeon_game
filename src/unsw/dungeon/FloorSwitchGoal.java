package unsw.dungeon;

public class FloorSwitchGoal extends FloorSwitch {

	private boolean goalComplete;
	
	public FloorSwitchGoal(int x, int y) {
		super(x, y);
		goalComplete = false;
	}

}
