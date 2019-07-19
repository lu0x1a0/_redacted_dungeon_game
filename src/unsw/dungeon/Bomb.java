package unsw.dungeon;

import javafx.scene.image.ImageView;

public class Bomb extends Entity implements Collectible {

	public Bomb(int x, int y) {
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
		return true;
	}

	@Override
	public void react(Entity e) {
		// TODO Auto-generated method stub
		
	}

}
