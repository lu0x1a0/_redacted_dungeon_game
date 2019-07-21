package unsw.dungeon;


import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class Bomb extends Collectible {
	
	@SuppressWarnings("unused") //Will be used in Milestone3
	private ImageView unlit;
	private ImageView blast;
	private Timer timer;
	public Bomb(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
		// TODO Auto-generated constructor stub
	}
	public void setImages(ImageView unlit, ImageView blast) {
		this.unlit = unlit;
		this.blast = blast;
	}
	@Override
	public void collect(Player player) {
		System.out.println("Collect Reached");
		player.addToInventory(this);
		System.out.println(player.getInventory());		
		this.removeFromView();
		collected  = true;
		this.postCollect();
	}
	@Override
	public void use(Object info) {
		if (info instanceof Player) {
			this.setIv(blast);
			x().set(((Player) info).getX());
			y().set(((Player) info).getY());
			dungeon.addEntity(this);
			dungeon.addToView(this,getIv());
			Bomb bomb = this;

			timer= new Timer(true);
			TimerTask task = new TimerTask() {
				@Override
			    public void run() {
					Platform.runLater(new Runnable() {
			            @Override public void run() {
							notifyObservers(bomb,"explode");
							bomb.removeFromView();
							timer.cancel();
			            }
			        });
				}
			};
			timer.scheduleAtFixedRate(task, 0, 1000);
			
			
			
			notifyObservers(bomb,"explode");
		}
	}
}
