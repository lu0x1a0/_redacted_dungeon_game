package unsw.dungeon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class EnemyPursuit implements EnemyPath {
	
	private Dungeon dungeon;
	public EnemyPursuit(Dungeon dungeon) {
		this.dungeon = dungeon;
	}
	@Override
	public Coord pathSearch(int startx, int starty) {
		HashMap<Coord,Coord> visited = new HashMap<Coord,Coord>();
		Coord ptr =  new Coord(startx,starty);
		Coord ori =  new Coord(startx,starty);
		Coord player = dungeon.getPlayerCoord();
		visited.put(ori, ori);
		Queue<Coord> queue = new LinkedList<Coord>();
		while ( ptr!=null && !ptr.equals(player)) {
			LinkedList<Coord> potential = dungeon.getSurroundPassable(ptr);
			for(Coord c:potential) {
				if(!visited.containsKey(c)) {
					visited.put(c, ptr);
					queue.add(c);
				}
			}
			ptr = queue.poll();
		}
		if (ptr.equals(player)) {
			Coord key = ptr;
			while(!visited.get(key).equals(ori)) {
				key = visited.get(key);
			}
			return key;

		}else {
			return null;
		}
	}

}
