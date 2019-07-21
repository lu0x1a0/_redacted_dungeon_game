package unsw.dungeon;


/**
 * Inherits from GoalLeafNode, is specifically linked to an
 * exit object. When that object is activated it changes status to
 * complete and notifies observers
 * @author Brendan
 *
 */
public class GoalLeafExit extends GoalLeafNode implements Observable, Observer {

	Exit finalExit; //Exit obj
	
	/**
	 * Initialised with the single exit object as there
	 * can only be one exit per map, as per spec
	 * @param theExit - exit that needs to be entered for goal to complete
	 */
	public GoalLeafExit(Exit theExit) {
		// TODO Auto-generated constructor stub
		finalExit = theExit;
		theExit.registerObserver(this);
		
	}

	/**
	 * returns whether or not this goal is complete
	 */
	@Override
	public boolean isComplete() {
		return finalExit.getPlayerIsTouching();
	}
	
	/**
	 * notify observers if the the goal is complete
	 */
	@Override
	public void update(Observable o, Object info) {
		if(o instanceof Exit) {
			if (isComplete() == true) {
				notifyObservers(this, true);
			}
		}
		
	}
	/**
	 * Testing method to print out all goals, in this 
	 * case it is a leaf node and prints just itself
	 */
	@Override
	public String printGoal(String message) {
		// TODO Auto-generated method stub
		return message + " Exit goal";
	}

}
