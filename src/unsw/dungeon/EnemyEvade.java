package unsw.dungeon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class EnemyEvade implements EnemyPath {
	
	private Dungeon dungeon;
	public EnemyEvade(Dungeon dungeon) {
		this.dungeon = dungeon;
	}

	@Override
	public Coord pathSearch(int startx, int starty) {
		HashMap<Coord,Coord> visited = new HashMap<Coord,Coord>();
		Coord player = dungeon.getPlayerCoord();
		Coord corner;
		if(player.getX()<dungeon.getWidth()/2 && player.getY()<dungeon.getHeight()/2) {
			corner = new Coord(dungeon.getWidth()-1, dungeon.getHeight()-1);
		}else if(player.getX()>dungeon.getWidth()/2 && player.getY()<dungeon.getHeight()/2) {
			corner = new Coord(0, dungeon.getHeight()-1);
		}else if(player.getX()<dungeon.getWidth()/2 && player.getY()>dungeon.getHeight()/2) {
			corner = new Coord(dungeon.getWidth()-1,0);
		}else {
			corner = new Coord(0,0);
		}
		Coord ptr =  new Coord(corner.getX(),corner.getY());
		Coord ori =  new Coord(corner.getX(),corner.getY());

		visited.put(ori, ori);
		Queue<Coord> queue = new LinkedList<Coord>();
		while (!dungeon.ispassable(ptr.getX(), ptr.getY())) {
			LinkedList<Coord> potential = dungeon.getSurroundTiles(ptr);
			for(Coord c:potential) {
				if(!visited.containsKey(c)) {
					visited.put(c, ptr);
					queue.add(c);
				}
			}
			ptr = queue.poll();
		}
		Coord target;
		if (dungeon.ispassable(ptr.getX(),ptr.getY())) {
			target = ptr;
		}else {
			return null;
		}
		
		HashMap<Coord,Coord> visited1 = new HashMap<Coord,Coord>();
		Coord ptr1 =  new Coord(startx,starty);
		Coord ori1 =  new Coord(startx,starty);
		visited1.put(ori1, ori1);
		Queue<Coord> queue1 = new LinkedList<Coord>();
		while ( ptr1!=null && !ptr1.equals(target)) {
			LinkedList<Coord> potential = dungeon.getSurroundPassable(ptr1);
			//queue.addAll(potential);
			for(Coord c:potential) {
				if(!visited1.containsKey(c)) {
					visited1.put(c, ptr1);
					queue1.add(c);
				}
			}
			ptr1 = queue1.poll();
		}
		if (ptr1.equals(target)) {
			Coord key = ptr1;
			while(!visited1.get(key).equals(ori1)) {
				key = visited1.get(key);
			}
			return key;

		}else {
			return null;
		}
	}
}

