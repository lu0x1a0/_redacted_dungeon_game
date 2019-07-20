package unsw.dungeon;

public class Treasure extends Collectible implements Observable {

	public Treasure(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void react(Entity e) {
		if(e instanceof Player) {
			collect((Player) e);
			notifyObservers(this, true);
		}
		
	}
	@Override
	public void use(Object info) {
		return;
	}

	public void postCollect() {
		// TODO Auto-generated method stub
		
	}

}
