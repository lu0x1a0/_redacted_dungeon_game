package unsw.dungeon;

import java.util.ArrayList;

/**
 * Inherits from GoalLeafNode, is specifically tailored to the 
 * goal of collecting all treasures
 * @author Brendan
 *
 */
public class GoalLeafTreasureCollected extends GoalLeafNode implements Observable, Observer {

	ArrayList<Treasure> Treasures = new ArrayList<Treasure>();
	
	/**
	 * Constructor with an input of ArrayList of treasure objects
	 * @param treasure - arrayList of treasure objects
	 */
	public GoalLeafTreasureCollected(ArrayList<Treasure> treasure) {
		for(Treasure t: treasure) {
			t.registerObserver(this);
			Treasures.add(t);
		}
	}
	
	/**
	 * Constructor with no parameters assuming the treasure will be added
	 * individually later
	 */
	public GoalLeafTreasureCollected() {
	}
	
	/**
	 * Add a single treasure obj to this goal
	 * @param t - treasure that needs to be collected for goal to be comeplete
	 */
	public void addTreasure(Treasure t) {
		t.registerObserver(this);
		Treasures.add(t);
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
	 * returns whether or not this goal is completed
	 */
	@Override
	public boolean isComplete() {
		for(Treasure t: Treasures) {
			if(t.isCollected() == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Testing method to print out all goals, in this 
	 * case it is a leaf node and prints just itself
	 */
	@Override
	public String printGoal(String message) {
		return message + " Treasure goal, i have count of: " + Treasures.size();
	}

}
