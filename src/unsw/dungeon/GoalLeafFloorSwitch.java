package unsw.dungeon;

import java.util.ArrayList;

public class GoalLeafFloorSwitch extends GoalLeafNode implements Observer, Observable {

	ArrayList<FloorSwitch> switches = new ArrayList<FloorSwitch>();
	
	public GoalLeafFloorSwitch(ArrayList<FloorSwitch> newSwitches) {
		for(FloorSwitch s: newSwitches) {
			s.registerObserver(this);
			switches.add(s);
		}
	}
	public GoalLeafFloorSwitch() {
	}
	
	public void addFloorSwitch(FloorSwitch fSwitch) {
		switches.add(fSwitch);
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
	@Override
	public String printGoal(String message) {
		// TODO Auto-generated method stub
		return message + " floor switch bitch";
	}

}
