package unsw.dungeon;

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
	public Potion(int x, int y, Dungeon dungeon ) {
        super(x, y, dungeon);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void use(Object info) {
		Potion p = this;
		new java.util.Timer().schedule( 
	        new java.util.TimerTask() {
	            @Override
	            public void run() {
	    			//notifyObservers(p,"invince");
	            	p.getDungeon().getPlayer().potionEffectOff(p);
	            }
	        }, 
	        2000 
		);
	}
	
	@Override
	public void postCollect() {
		use(dungeon.getPlayer());
	}

}
