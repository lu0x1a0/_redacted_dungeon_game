package unsw.dungeon;

public class Key extends Collectible {
	private int id;
	public Key(int x, int y, Dungeon dungeon,int id) {
        super(x, y, dungeon);
        this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public void use(Object info) {
		if(info instanceof Player) {
			((Player) info).removeKey();
		}
	}
	@Override
	public void react(Entity e) {
		if (e instanceof Player) {
			Player p = (Player) e;
			if (p.getKey() == null) {
				collect((Player) e);
			}
			System.out.println("picking key---");
		}
	}
	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return true;
	}

}
