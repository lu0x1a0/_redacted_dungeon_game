package unsw.dungeon;

import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Invinciblity potion collectible for the player
 * @author Brendan
 *
 */
public class Potion extends Collectible {

	/**
	 * Constructor for the potion class
	 * @param x - int
	 * @param y - int
	 * @param dungeon - dungeon to add to
	 */
	private int time;
	private BooleanProperty onUse;
	public Potion(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
        time = 5;
        onUse = new SimpleBooleanProperty();
        onUse.setValue(false);
	}
	
	@Override
	public void use(Object info) {
		Potion p = this;
		onUse.set(true);
		notifyObservers(p,"on");
	}
	
	@Override
	public void postCollect() {
		use(null);
	}
	public void pauseuse() {
		onUse.set(false);
		notifyObservers(this,"off");
	}
	public void wearoff() {
		onUse.set(false);
		notifyObservers(this,"off");
		getDungeon().getPlayer().potionEffectOff(this);
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public BooleanProperty getOnUse() {
		return onUse;
	}	
	@Override
	public void react(Entity e) {
		if (e instanceof Player) {
			Player p = (Player) e;
			if (!p.hasPotion()) {
				collect((Player) e);
			}
		}
	}
}
