package unsw.dungeon;

public interface Collectible {
	public boolean isCollected();
	public void use();
	public default void collect(Player player) {
		//Player.
	}
}
