package unsw.dungeon;

import javafx.scene.image.ImageView;

public class Door extends Entity {
	private boolean isopen = false;
	public Door(int x, int y, ImageView iv) {
        super(x, y);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return isopen;
	}
	@Override
	public void react(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
