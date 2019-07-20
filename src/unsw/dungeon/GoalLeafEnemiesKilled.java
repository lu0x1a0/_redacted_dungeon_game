package unsw.dungeon;

import java.util.ArrayList;

public class GoalLeafEnemiesKilled extends GoalLeafNode implements Observable, Observer {

	ArrayList<Enemy> Enemies = new ArrayList<Enemy>();
	
	public GoalLeafEnemiesKilled(ArrayList<Enemy> enemies) {
		for(Enemy e: enemies) {
			e.registerObserver(this);
			Enemies.add(e);
		}
	}
	
	public GoalLeafEnemiesKilled() {
	}

	public void addEnemy(Enemy e) {
		Enemies.add(e);
	}
	
	@Override
	public String printGoal(String message) {
		// TODO Auto-generated method stub
		return message + " enemies goal";
	}
	
	
	@Override
	public void update(Observable o, Object info) {
		if(isComplete() == true) {
			notifyObservers(this, true);
		}

	}

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
