package unsw.dungeon;

public interface SwitchState {
	public SwitchState press(Movable m);
	public SwitchState free();
}
