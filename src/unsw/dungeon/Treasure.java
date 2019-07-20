package unsw.dungeon;

public class Treasure extends Collectible implements Observable {


	private boolean collected = false;
	
	public Treasure(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isCollected() {
		// TODO Auto-generated method stub
		return collected;
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
	public void collect(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void use() {
		return;
		
	}

	public void postCollect() {
		// TODO Auto-generated method stub
		
	}

}
