package unsw.dungeon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import java.util.Timer;
import java.util.TimerTask;


import javafx.application.Platform;


/**
 * Enemy entity that aims to kill the player
 * being observed by the dungeon, observing the player
 * @author Brendan
 *
 */
public class Enemy extends Movable {
	private Timer timer;

	private long speed;
	Dungeon dungeon;
	boolean alive = true;
	
	private EnemyPath evade;
	private EnemyPath pursuit;
	private EnemyPath activeMethod;
	public Enemy(int x, int y, Dungeon dungeon) {
		super(x, y, dungeon);
		this.dungeon = dungeon;
		evade = new EnemyEvade(dungeon);
		pursuit = new EnemyPursuit(dungeon);
		activeMethod = pursuit;
	}
	/**
	 * let the enemy to start moving in the dungeon
	 * @param difficulty
	 */
	public void start(String difficulty) {
		Enemy e = this;
		timer= new Timer(true);
		switch (difficulty) {
		case "Easy":
			speed = 2000;
			break;
		case "Medium":
			speed = 1000;
			break;
		case "Hard":
			speed = 500;
			break;
		}
		TimerTask task = new TimerTask() {
			@Override
		    public void run() {
		    	Coord c = e.pathSearch();
				Coord old = new Coord(e.getX(),e.getY());
				//sets new coord
				e.x().set(c.getX());
				e.y().set(c.getY());
				Platform.runLater(new Runnable() {
		            @Override public void run() {
						notifyObservers(e,old);
		            }
		        });
			}
		};
		timer.scheduleAtFixedRate(task, 0, speed);
	}

	@Override
	public void moveUp() {
	}

	@Override
	public void moveDown() {
	}

	@Override
	public void moveLeft() {
	}

	@Override
	public void moveRight() {
	}

	@Override
	public boolean isPassable() {
		return true;
	}
	/**
	 * BFS to find shortest path to player
	 * @return - cooridinate to move to
	 */
	public Coord pathSearch() {
		return activeMethod.pathSearch(getX(), getY());
	}
	
	
	/**
	 * returns boolean of whether or not the enemy is alive or has been killed
	 * @return - boolean
	 */
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
				((Player) e).dead();
				((Player) e).removeFromView();
				timer.cancel();
			}
		}
		if (e instanceof Boulder) {
			killed();
		}
	}
	
	/**
	 * method called to kill enemy
	 */
	public void killed() {
		removeFromView();
		notifyObservers(this,"dead");
		alive = false;
		if (timer != null) {
			timer.cancel();
		}
	}
	public void setPathMethod(boolean on) {
		if(on) {
			activeMethod = evade;
		}else {
			activeMethod = pursuit;
		}
	}
}
