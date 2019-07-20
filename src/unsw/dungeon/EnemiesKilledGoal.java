package unsw.dungeon;

import java.util.ArrayList;

public class EnemiesKilledGoal extends GoalLeafNode implements Observable, Observer {

	ArrayList<Enemy> Enemies = new ArrayList<Enemy>();
	
	public EnemiesKilledGoal(ArrayList<Enemy> enemies) {
		for(Enemy e: enemies) {
			e.registerObserver(this);
			Enemies.add(e);
		}
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
