package unsw.dungeon;

import java.util.ArrayList;

public class GoalLeafTreasureCollected extends GoalLeafNode implements Observable, Observer {

	ArrayList<Treasure> Treasures = new ArrayList<Treasure>();
	
	public GoalLeafTreasureCollected(ArrayList<Treasure> treasure) {
		for(Treasure t: treasure) {
			t.registerObserver(this);
			Treasures.add(t);
		}
	}
	
	public GoalLeafTreasureCollected() {
	}
	
	public void addTreasure(Treasure t) {
		t.registerObserver(this);
		Treasures.add(t);
	}

	@Override
	public void update(Observable o, Object info) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isComplete() {
		for(Treasure t: Treasures) {
			if(t.isCollected() == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String printGoal(String message) {
		// TODO Auto-generated method stub
		return message + " Treasure goal";
	}

}
