package unsw.dungeon;

public class FloorSwitch extends Entity {

	public FloorSwitch(int x, int y, Dungeon dungeon ) {
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
		// TODO Auto-generated method stub
		
	}

}
