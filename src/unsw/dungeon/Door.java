package unsw.dungeon;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * Door entity which is impassable until key is collected
 * @author Brendan
 *
 */
public class Door extends Entity {
	//@SuppressWarnings("unused") //Will be used in milestone3
	private ImageView lock;
	//@SuppressWarnings("unused") //Will be used in milestone3
	private ImageView unlock;
	private boolean isopen = false;
	private int id;
	/**
	 * A door can only be opened(become passable by in contact with a key of same ID)
	 * @param x - int
	 * @param y - int
	 * @param dungeon - Dungeon
	 * @param id - int
	 */
	public Door(int x, int y, Dungeon dungeon, int id) {
        super(x, y, dungeon);
        this.id = id;
	}
	/**
	 * 
	 * @param lock - ImageView
	 * @param unlock - ImageView
	 */
	public void setViews(ImageView lock, ImageView unlock) {
		this.unlock = unlock;
		this.lock = lock;
	}
	/**
	 * return the id of the door
	 * @return - Int
	 */
	public int getId() {
		return id;
	}
	/**
	 * change passable status of the Door(after used key)
	 * @param isopen - boolean
	 */
	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
		this.removeFromView();

	}

	@Override
	public boolean isPassable() {
		return isopen;
	}
	@Override
	public void react(Entity e) {
		if(e instanceof Player) {
			if( ((Player) e).getKey()!=null &&
				((Player) e).getKey().getId() == getId()) {
				((Player) e).removeKey();
				//setIsopen(true);
				isopen = true;
				this.removeFromView();
				this.setIv(unlock);
				dungeon.addToView(this, getIv());
				//dungeon.changeEntityImage(this, lock);
				for(Node node:dungeon.getDc().squares.getChildren()) {
					if (dungeon.getDc().squares.getColumnIndex(node).intValue() == getX() && dungeon.getDc().squares.getRowIndex(node).intValue() == getY())
						System.out.println( ((ImageView)node).getImage().impl_getUrl() );
				}
				System.out.println( "++++"+getIv().getImage().impl_getUrl() );
				
				
			}
		}
	}

}
