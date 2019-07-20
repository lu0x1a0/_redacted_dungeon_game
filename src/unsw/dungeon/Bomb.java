package unsw.dungeon;

import javafx.scene.image.ImageView;

public class Bomb extends Collectible {

	public Bomb(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isCollected() {
		return false;
		
	}

	@Override
	public void postCollect() {
		// TODO Auto-generated method stub
		return;
	}
	@Override
	public void use() {
		// TODO Auto-generated method stub

	}
}
