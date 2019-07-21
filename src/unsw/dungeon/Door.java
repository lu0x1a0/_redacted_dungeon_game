package unsw.dungeon;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class Door extends Entity {
	private ImageView lock;
	private ImageView unlock;
	private boolean isopen = false;
	private int id;
	public Door(int x, int y, Dungeon dungeon, int id) {
        super(x, y, dungeon);
        this.id = id;
	}
	public void setViews(ImageView lock, ImageView unlock) {
		this.unlock = lock;
		this.lock = lock;
	}
	
	public int getId() {
		return id;
	}

	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
		Door d = this;
//		Platform.runLater(new Runnable() {
//            @Override 
//            public void run() {
//				//notifyObservers(e,old);
//            	dungeon.changeEntityImage(d, d.lock);
//            }
//        });
		this.removeFromView();
		//this.setIv(unlock);
		//dungeon.addEntity(this);
		//dungeon.addToView(this,getIv());
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
				System.out.println("Entered");
			}
		}
	}

}
