package unsw.dungeon;

public abstract class GoalLeafNode implements GoalComponent {

	public GoalLeafNode() {
		// TODO Auto-generated constructor stub
	}

	@Override
	abstract public boolean isComplete();
	
	
	@Override
	public void notifyObservers(Observable e, Object info) {
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
