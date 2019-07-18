package unsw.dungeon;

public class Enemy extends Entity implements Movable {

	public Enemy(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkPositionAvail() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return true;
	}

}
