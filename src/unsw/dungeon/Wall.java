package unsw.dungeon;

public class Wall extends Entity {

    public Wall(int x, int y) {
        super(x, y);
    }

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return false;
	}

}
