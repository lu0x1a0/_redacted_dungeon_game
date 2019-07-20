package unsw.dungeon;

import java.util.ArrayList;

public class TreasureCollectedGoal extends GoalLeafNode implements Observable, Observer {

	ArrayList<Treasure> Treasures = new ArrayList<Treasure>();
	
	public TreasureCollectedGoal(ArrayList<Treasure> treasure) {
		for(Treasure t: treasure) {
			t.registerObserver(this);
			Treasures.add(t);
		}
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

}
