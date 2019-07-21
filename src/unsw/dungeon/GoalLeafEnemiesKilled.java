package unsw.dungeon;

import java.util.ArrayList;


/**
 * Inherits from GoalLeafNode, is specifically tailored to the 
 * goal of killing all enemies
 * @author Brendan
 *
 */
public class GoalLeafEnemiesKilled extends GoalLeafNode implements Observable, Observer {

	ArrayList<Enemy> Enemies = new ArrayList<Enemy>();
	
	/**
	 * Can take in a list of enemies in constructor to initialise itself
	 * @param enemies - Enemies that need to be killed for goal to complete
	 */
	public GoalLeafEnemiesKilled(ArrayList<Enemy> enemies) {
		for(Enemy e: enemies) {
			e.registerObserver(this);
			Enemies.add(e);
		}
	}
	
	/**
	 * Constructor to initialise itself and then other methods
	 * are used to add specific enemies
	 */
	public GoalLeafEnemiesKilled() {
	}

	/**
	 * Function to add a specific enemy to this goal
	 * @param e - enemy to add
	 */
	public void addEnemy(Enemy e) {
		Enemies.add(e);
	}
	
	/**
	 * Testing method to print out all goals, in this 
	 * case it is a leaf node and prints just itself
	 */
	@Override
	public String printGoal(String message) {
		// TODO Auto-generated method stub
		return message + " enemies goal";
	}
	
	/**
	 * Notifies observers if it is complete
	 */
	@Override
	public void update(Observable o, Object info) {
		if(isComplete() == true) {
			notifyObservers(this, true);
		}

	}
	
	/**
	 * returns whether or not this goal is complete
	 */
	@Override
	public boolean isComplete() {
		for(Enemy e: Enemies) {
			if(e.isAlive() == true) {
				return false;
			}
		}
		return true;
	}

}
