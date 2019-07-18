package unsw.dungeon;

public class Door extends Entity {
	private boolean isopen = false;
	public Door(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return isopen;
	}

}
