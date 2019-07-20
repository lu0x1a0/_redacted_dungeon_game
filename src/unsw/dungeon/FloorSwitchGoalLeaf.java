package unsw.dungeon;

import java.util.ArrayList;

public class FloorSwitchGoalLeaf extends GoalLeafNode implements Observer, Observable {

	ArrayList<FloorSwitch> switches = new ArrayList<FloorSwitch>();
	
	public FloorSwitchGoalLeaf(ArrayList<FloorSwitch> newSwitches) {
		for(FloorSwitch s: newSwitches) {
			s.registerObserver(this);
			switches.add(s);
		}
	}
	
	@Override
	public boolean isComplete() {
		for(FloorSwitch s: switches) {
			if(s.isPressed() == false) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void update(Observable o, Object info) {
		if(isComplete() == true) {
			notifyObservers(this, true);
		}
	}

}
