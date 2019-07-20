package unsw.dungeon;

public class ExitGoal extends Entity implements GoalComponent, Observer, Observable {
	private boolean otherGoalsCompleted = false;
	private boolean isComplete;

	public ExitGoal(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
		isComplete = false;
	}

	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return isComplete;
	}

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return otherGoalsCompleted;
	}

	@Override
	public void react(Entity e) {
		if(e instanceof Player) {
			((Player) e).registerObserver(this);
			isComplete = true;
			
		}
		
	}

	@Override
	public void update(Observable o, Object info) {
		if(o instanceof Player) {
			if(((Player) o).getX() != getX() || ((Player) o).getY() != getY()){
				isComplete = false;
				((Player) o).removeObserver(this);
			}
		}
		
	}

}
