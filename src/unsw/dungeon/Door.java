package unsw.dungeon;

import javafx.scene.image.ImageView;

public class Door extends Entity {
	@SuppressWarnings("unused")
	private ImageView lock;
	@SuppressWarnings("unused")
	private ImageView unlock;
	private boolean isopen = false;
	private int id;
	/**
	 * A door can only be opened(become passable by in contact with a key of same ID)
	 * @param x coord
	 * @param y coord
	 * @param dungeon
	 * @param id
	 */
	public Door(int x, int y, Dungeon dungeon, int id) {
        super(x, y, dungeon);
        this.id = id;
	}
	/**
	 * 
	 * @param lock
	 * @param unlock
	 */
	public void setViews(ImageView lock, ImageView unlock) {
		this.unlock = lock;
		this.lock = lock;
	}
	/**
	 * return the id of the door
	 * @return
	 */
	public int getId() {
		return id;
	}
	/**
	 * change passable status of the Door(after used key)
	 * @param isopen
	 */
	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
		this.removeFromView();

	}

	@Override
	public boolean ispassable() {
		return isopen;
	}
	@Override
	public void react(Entity e) {
		if(e instanceof Player) {
			if( ((Player) e).getKey()!=null &&
				((Player) e).getKey().getId() == getId()) {
				((Player) e).removeKey();
				setIsopen(true);
			}
		}
	}

}
