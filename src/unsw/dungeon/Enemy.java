package unsw.dungeon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javafx.scene.image.ImageView;

public class Enemy extends Movable {
// being observed by the dungeon, observing the player
	Dungeon dungeon;
	boolean alive = true;
	public Enemy(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.dungeon = dungeon;
	}
	public void start() {
		
	}
	@Override
	public boolean checkPositionAvail() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean ispassable() {
		// TODO Auto-generated method stub
		return true;
	}
	public Coord pathsearch() {
		// THIS_coord, coord came from
		HashMap<Coord,Coord> visited = new HashMap<Coord,Coord>();
		Coord ptr =  new Coord(getX(),getY());
		Coord ori =  new Coord(getX(),getY());
		Coord player = dungeon.getPlayerCoord();
		
		Queue<Coord> queue = new LinkedList<Coord>();
		while ( ptr!=null || ptr != player) {
			LinkedList<Coord> potential = dungeon.getSurroundPassable(ptr);
			//queue.addAll(potential);
			for(Coord c:potential) {
				if(!visited.containsKey(c)) {
					visited.put(c, ptr);
					queue.add(c);
				}
			}
			ptr = queue.poll();
		}
		if (ptr == player) {
			Coord key = ptr;
			while(visited.get(key)!=ori) {
				key = visited.get(key);
			}
			return key;
		}else {
			return null;
		}
	}
	
	
	
	public boolean isAlive() {
		return alive;
	}
	
	@Override
	public void react(Entity e) {
		if (e instanceof Sword) {
			Sword s = (Sword) e;
			s.setCount(s.getCount()-1);
			killed();
		}
		if (e instanceof Player) {
			if( ((Player) e).hasPotion() ) {
				killed();	
			}else {
				((Player) e).removeFromView();
			}
		}
	}
	public void killed() {
		removeFromView();
		notifyObservers(this,"dead");
		alive = false;
	}
}
