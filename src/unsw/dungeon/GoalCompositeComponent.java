package unsw.dungeon;

import java.util.ArrayList;


/**
 * The composite component in the in composite pattern for goals
 * Works as a hierarchy where this is either an AND goal type or OR
 * uses that operator to combine sub goals
 * @author Brendan
 *
 */
public class GoalCompositeComponent implements GoalComponent{

	/**
	 * If true then the goal type is an AND condition
	 */
	private boolean andFlag; 
	
	private ArrayList<GoalComponent> goals = new ArrayList<GoalComponent>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	/**
	 * Constructor for composite component
	 * @param flag - True for AND, false for OR
	 */
	public GoalCompositeComponent(boolean flag) {
		this.andFlag = flag;
	}

	
	/**
	 * Returns whether or not this goal is complete,
	 * Will check all subgoals and return the result depending on
	 * whether this class is AND or OR
	 */
	@Override
	public boolean isComplete() {
		if(andFlag == true) {
			for(GoalComponent g: goals) {
				if(g.isComplete() == false) {
					return false;
				}
			}
			return true;
		}
		else {
			for(GoalComponent g: goals) {
				if(g.isComplete() == true) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Used for testing, it prints out all the subgoals, below it in tree
	 */
	@Override
	public String printGoal(String message) {
		String addedMessage = message;
		for(GoalComponent g: goals) {
			addedMessage += " + " + g.printGoal("");
		}
		return addedMessage;
	}

	
	/**
	 * Adds a GoalComponent node to itself
	 * Could be another Composite component or a leaf node
	 * Attaches the child goal and registers itself as an
	 * observer so when the child goal changes it will check to
	 * see if itself is complete
	 * @param child - GoalComponent, subgoal
	 */
	public void addChild(GoalComponent child) {
		goals.add(child);
		child.registerObserver(this);
	}

	
	/**
	 * Called when a subgoal changes status, will then calculate
	 * whether or not itself is then completed and notify the
	 * parent goal above it
	 */
	@Override
	public void update(Observable o, Object info) {
		if(isComplete() == true) {
			notifyObservers(this, true);
		}
		
	}

	/**
	 * Standard notify observers function
	 */
	@Override
	public void notifyObservers(Observable e, Object info) {
		for(Observer o: observers) {
			o.update(e, info);
		}
	}
	
	/**
	 * Standard remove observer from this object
	 */
	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	
	
	/**
	 * Standard add observer to this object
	 */
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}
	
}
