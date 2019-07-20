package unsw.dungeon;

public class Sword extends Collectible {
	private int count;
	public Sword(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
        count = 5;
	}

	@Override
	public boolean isCollected() {
		// TODO Auto-generated method stub
		return false;
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public void use() {
		notifyObservers(this,0);
	}
	@Override
	public void react(Entity e) {
		if (e instanceof Player) {
			Player p = (Player) e;
			if (!p.hasSword()) {
				System.out.println(p.hasSword());
				collect((Player) e);
			}
		}
	}
	@Override
	public void postCollect() {
		// TODO Auto-generated method stub
		
	}

}
