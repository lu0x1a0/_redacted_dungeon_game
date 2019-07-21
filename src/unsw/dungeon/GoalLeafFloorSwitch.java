package unsw.dungeon;

import java.util.ArrayList;

/**
 * Inherits from GoalLeafNode, is specifically tailored to the 
 * floor switches
 * @author Brendan
 *
 */
public class GoalLeafFloorSwitch extends GoalLeafNode implements Observer, Observable {

	ArrayList<FloorSwitch> switches = new ArrayList<FloorSwitch>();
	
	/**
	 * Constructor with an input of ArrayList of switches
	 * @param newSwitches - all floor switches this goal needs to check
	 */
	public GoalLeafFloorSwitch(ArrayList<FloorSwitch> newSwitches) {
		for(FloorSwitch s: newSwitches) {
			s.registerObserver(this);
			switches.add(s);
		}
	}
	/**
	 * Constructor with no parameters assuming the floor switches will be added
	 * individually later
	 */
	public GoalLeafFloorSwitch() {
	}
	
	/**
	 * Add a single floor switch to this goal
	 * @param fSwitch
	 */
	public void addFloorSwitch(FloorSwitch fSwitch) {
		switches.add(fSwitch);
	}
	
	/**
	 * returns whether or not this goal is complete
	 */
	@Override
	public boolean isComplete() {
		for(FloorSwitch s: switches) {
			if(s.isPressed() == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * notifies observers if it is completed
	 */
	@Override
	public void update(Observable o, Object info) {
		if(isComplete() == true) {
			
			notifyObservers(this, true);
		}
	}
	
	/**
	 * Testing method to print out all goals, in this 
	 * case it is a leaf node and prints just itself
	 */
	@Override
	public String printGoal(String message) {
		
		return message + " floor switch";
	}

}
