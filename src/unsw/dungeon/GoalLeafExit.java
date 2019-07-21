package unsw.dungeon;

public class GoalLeafExit extends GoalLeafNode implements Observable, Observer {

	Exit finalExit;
	
	public GoalLeafExit(Exit theExit) {
		// TODO Auto-generated constructor stub
		finalExit = theExit;
		
	}

	@Override
	public boolean isComplete() {
		return finalExit.getPlayerIsTouching();
	}
	
	@Override
	public void update(Observable o, Object info) {
		if(o instanceof Exit) {
			if (isComplete() == true) {
				notifyObservers(this, true);
			}
		}
		
	}

	@Override
	public String printGoal(String message) {
		// TODO Auto-generated method stub
		return message + " Exit goal";
	}

}
