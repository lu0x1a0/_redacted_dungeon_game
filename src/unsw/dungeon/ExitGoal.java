package unsw.dungeon;

public class ExitGoal extends Entity implements Goal {
	private boolean otherGoalsCompleted = false;

	public ExitGoal(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean iscomplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setComplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return otherGoalsCompleted;
	}

}
