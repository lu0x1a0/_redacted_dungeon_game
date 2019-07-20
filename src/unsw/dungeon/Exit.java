package unsw.dungeon;

public class Exit extends Entity implements Observer, Observable {
	private boolean playerIsTouching = false;

	public Exit(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
	}


	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void react(Entity e) {
		if(e instanceof Player) {
			((Player) e).registerObserver(this);
			setPlayerIsTouching(true);
		}
		
	}
	
	
	

	public void setPlayerIsTouching(boolean playerIsTouching) {
		notifyObservers(this, playerIsTouching);
		this.playerIsTouching = playerIsTouching;
	}


	public boolean isPlayerIsTouching() {
		return playerIsTouching;
	}


	@Override
	public void update(Observable o, Object info) {
		if(o instanceof Player) {
			if(((Player) o).getX() != getX() || ((Player) o).getY() != getY()){
				setPlayerIsTouching(false);
				((Player) o).removeObserver(this);
			}
		}
		
	}

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
