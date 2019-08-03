package unsw.dungeon;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;

/**
 * collectible that can be used to kill movables on the map
 * @author Luoxi
 *
 */
public class Bomb extends Collectible {
	
	private ImageView unlit;
	private ImageView lit1;
	private ImageView lit2;
	private ImageView lit3;
	private ImageView blast;
	private Timer timer;
	private BooleanProperty islit;
	public Bomb(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
        this.islit = new SimpleBooleanProperty();
        islit.set(false);
	}
	/**
	 * setter for different image state of the entity
	 * @param unlit - image
	 * @param blast - image
	 */
	public void setImages(ImageView unlit, ImageView lit1, ImageView lit2, ImageView lit3, ImageView blast) {
		this.unlit = unlit;
		this.lit1 = lit1;
		this.lit2 = lit2;
		this.lit3 = lit3;
		this.blast = blast;
	}
	public BooleanProperty getIslit() {
		return islit;
	}
	@Override
	public void collect(Player player) {
		player.addToInventory(this);
		this.removeFromView();
		collected  = true;
		this.postCollect();
	}
	
	/**
	 * Action to light bomb
	 */
	@Override
	public void use(Object info) {
		if (info instanceof Player) {
			x().set(((Player) info).getX());
			y().set(((Player) info).getY());
			dungeon.addEntity(this);
			this.lit1();
			islit.set(true);
			Bomb bomb = this;
//			timer= new Timer(true);
//			TimerTask task = new TimerTask() {
//				@Override
//			    public void run() {
//					Platform.runLater(new Runnable() {
//			            @Override public void run() {
//			            	notifyObservers(bomb,"explode");
//							bomb.removeFromView();
//							timer.cancel();
//			            }
//			        });
//				}
//			};
//			timer.scheduleAtFixedRate(task, 1000, 1000);
		}
	}
	public void lit1() {
		this.setIv(lit1);
		dungeon.addToView(this,getIv());
	}
	public void lit2() {
		//this.removeFromView();
		this.setIv(lit2);
		//dungeon.addToView(this,getIv());
		dungeon.changeEntityImage(this,lit1);
	}
	public void lit3() {
		this.setIv(lit3);
		dungeon.changeEntityImage(this,lit2);
	}
	public void explode() {
		this.setIv(blast);
		dungeon.changeEntityImage(this,lit3);
	}
	@Override
	public String toString() {
		return this.getIv().toString();
	}
}
