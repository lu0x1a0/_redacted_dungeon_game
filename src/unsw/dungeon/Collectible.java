package unsw.dungeon;

public interface Collectible {
	public boolean iscollected();
	public void use();
	public default void collect(Player player) {
		//Player.
	}
}
