package unsw.dungeon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;

import javafx.scene.image.ImageView;

public class Bomb extends Collectible {
	private ImageView unlit;
	private ImageView blast;
	
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
	public void postCollect() {
		// TODO Auto-generated method stub
		return;
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
//			Timer timer = new Timer(3000, new ActionListener() {
//				@Override
//				public void actionPerformed(ActionEvent arg0) {
//				    // Code to be executed
//					//System.out.printf();
//					 		
//					/*try {
//						TimeUnit.SECONDS.sleep(1);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} */
//					
//					//bomb.removeFromView();
//				}
//			});
//			timer.setRepeats(false); // Only execute once
//			timer.start(); 
//			while(timer.isRunning()) {
//				
//			}

//			new java.util.Timer().schedule( 
//			        new java.util.TimerTask() {
//			            @Override
//			            public void run() {
//			    			notifyObservers(bomb,"explode");			            
//						}
//			        }, 
//			        3000 
//			);
//			System.out.print("asasasas");
			notifyObservers(bomb,"explode");
			bomb.removeFromView();
		}
	}
}
