package unsw.dungeon;

public class FloorSwitch extends Entity {

	private boolean isPressedDown = false;
	
	public FloorSwitch(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return true;
	}

	public void activatePressed() {
		isPressedDown = true;
	}
	
	public void deactivatePressed() {
		isPressedDown = false;
	}
	
	public boolean isPressed() {
		return isPressedDown;
	}

	@Override
	public void react(Entity obj) {
		if(obj instanceof Movable) {
			isPressedDown = true;
		}
		
	}

}
