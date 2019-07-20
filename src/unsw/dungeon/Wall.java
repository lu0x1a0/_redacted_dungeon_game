package unsw.dungeon;

public class Wall extends Entity {

    public Wall(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
    }

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void react(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
