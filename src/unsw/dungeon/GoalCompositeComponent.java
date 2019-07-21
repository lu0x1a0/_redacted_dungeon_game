package unsw.dungeon;

import java.util.ArrayList;

public class GoalCompositeComponent implements GoalComponent{

	private boolean andFlag; //If true then the goal type is an AND condition
	private ArrayList<GoalComponent> goals = new ArrayList<GoalComponent>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	
	public GoalCompositeComponent(boolean flag) {
		// TODO Auto-generated constructor stub
		this.andFlag = flag;
	}

	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
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
	
	

	@Override
	public String printGoal(String message) {
		String addedMessage = message;
		System.out.println(goals);
		for(GoalComponent g: goals) {
			System.out.println(g);
			addedMessage += " + " + g.printGoal("");
		}
		System.out.println(observers);
		return addedMessage;
	}

	public void addChild(GoalComponent child) {
		goals.add(child);
		child.registerObserver(this);
	}

	@Override
	public void update(Observable o, Object info) {
		if(isComplete() == true) {
			notifyObservers(this, true);
		}
		
	}

	@Override
	public void notifyObservers(Observable e, Object info) {
		System.out.println(observers);
		for(Observer o: observers) {
			o.update(e, info);
		}
	}
	
	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	
	@Override
	public void registerObserver(Observer o) {
		observers.add(o);
	}
	
}
