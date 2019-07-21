package unsw.dungeon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import java.util.Timer;
import java.util.TimerTask;


import javafx.application.Platform;

public class Enemy extends Movable {
// being observed by the dungeon, observing the player
	private Timer timer;
	//static int count = 0;
	Dungeon dungeon;
	boolean alive = true;
	public Enemy(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.dungeon = dungeon;
		//start();
	}
	public void start() {
		Enemy e = this;
		timer= new Timer(true);
		TimerTask task = new TimerTask() {
			@Override
		    public void run() {
		    	//System.out.println(e.pathSearch());
				Coord c = e.pathSearch();
				//count ++;
				Coord old = new Coord(e.getX(),e.getY());
				e.x().set(c.getX());
				e.y().set(c.getY());
				//notifyObservers(e,old);
				Platform.runLater(new Runnable() {
		            @Override public void run() {
						notifyObservers(e,old);
		            }
		        });
			}
		};
		timer.scheduleAtFixedRate(task, 0, 1000);
		//System.out.println(e.pathSearch());

		
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
	public Coord pathSearch() {
		// THIS_coord, coord came from		
		HashMap<Coord,Coord> visited = new HashMap<Coord,Coord>();
		Coord ptr =  new Coord(getX(),getY());
		Coord ori =  new Coord(getX(),getY());
		Coord player = dungeon.getPlayerCoord();
		visited.put(ori, ori);
		System.out.println("-----------START-----------");
		System.out.println(player);
		System.out.println("---------------------------");	
		Queue<Coord> queue = new LinkedList<Coord>();
		while ( ptr!=null && !ptr.equals(player)) {
			System.out.println("--iter--");	
			System.out.println(ptr);
			LinkedList<Coord> potential = dungeon.getSurroundPassable(ptr);
			//queue.addAll(potential);
			for(Coord c:potential) {
				if(!visited.containsKey(c)) {
					visited.put(c, ptr);
					queue.add(c);
				}
			}
			System.out.println(queue);
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
	
	
	
	public boolean isAlive() {
		return alive;
	}
	
	@Override
	public void react(Entity e) {
		//timer.cancel();
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
				timer.cancel();
			}
		}
		if (e instanceof Boulder) {
			killed();
		}
	}
	public void killed() {
		removeFromView();
		notifyObservers(this,"dead");
		alive = false;
		if (timer != null) {
			timer.cancel();
		}
	}
}
