package unsw.dungeon;

import javafx.scene.image.ImageView;

public class Door extends Entity {
	private boolean isopen = false;
	private int id;
	public Door(int x, int y, Dungeon dungeon, int id) {
        super(x, y, dungeon);
        this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
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
