package unsw.dungeon;

public class Treasure extends Entity implements Collectible {

	public Treasure(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean iscollected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return true;
	}

}
